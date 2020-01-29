package examples.springmvc.controller;

import examples.springmvc.dto.*;
import examples.springmvc.service.BoardService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "/boards")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping
    public String list(@RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "kind", required = false) String kind,
            @RequestParam(name = "value", required = false) String value,
            ModelMap modelMap){
        int count = 5;
        int offset = page * count - count; // 1page가 시작이다. 1* 5- 5 = 0 , 1페이지의 경우 offset은 0 이되고 2페이지의 경우 offset은 5가 된다.
        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setCount(count);
        searchCondition.setOffset(offset);
        if(kind != null && value != null) {
            searchCondition.setKind(kind);
            searchCondition.setValue("%" + value + "%"); // like 검색을 하도록 한다. option에 값이 없을 경우에는 where 조건이 붙지 않는다.
        }

        int boardCount = boardService.getBoardCount(searchCondition);
        int pageCount = boardCount / count;
        if(boardCount % count > 0) pageCount++;

        List<PageInfo> pageNavi = new ArrayList<>(); // 현재페이지를 기준으로 앞뒤 페이지 2개만 붙여서 링크를 만든다.
        int start = 1;
        int end = pageCount;
        if(page > 2)
            start = page - 2;
        if(page <= pageCount - 2)
            end = page + 2;

        try {
            for (int i = start; i <= end; i++) {
                pageNavi.add(new PageInfo(i, kind, value));
            }
        }catch(Exception ex){
            System.out.println("encoding error");
        }



        List<Board> boards = boardService.getBoards(searchCondition);
        modelMap.addAttribute("list", boards);
        modelMap.addAttribute("count", boardCount);
        modelMap.addAttribute("pageNavi", pageNavi);
       
        return "boards/list";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable(name = "id")Long id, ModelMap modelMap){
        Board board = boardService.getBoard(id);
        List<BoardFile> boardFiles = boardService.getBoardFiles(id);
        modelMap.addAttribute("board", board);
        modelMap.addAttribute("boardFiles", boardFiles);
        return "boards/view";
    }

    @GetMapping("/download/{boardFileId}")
    @ResponseBody // controller에서 view를 사용하지 않고 직접 출력할 때 사용한다.
    public void fileDownload(@PathVariable(name = "boardFileId")Long boardFileId, HttpServletResponse httpServletResponse){
        try {
            OutputStream out = httpServletResponse.getOutputStream();
            BoardFile boardFile = boardService.getBoardFile(boardFileId);

            // 파일을 무조건 다운로드 되도록 한다.
            String fileName = URLDecoder.decode(boardFile.getFileName(), "ISO8859_1");
            httpServletResponse.setContentType("application/x-msdownload");
            httpServletResponse.setHeader("Content-disposition", "attachment; filename="+ fileName);

            httpServletResponse.setContentLength(boardFile.getFileLength());
            try(FileInputStream fis = new FileInputStream(boardFile.getSaveFileName())){
                IOUtils.copy(fis, out); //파일로 부터 읽어들여 응답에 출력한다.
            }catch(Exception ex2){
                ex2.printStackTrace();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @GetMapping("/writeform")
    public String writeform(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        Object user = session.getAttribute("login");
        if(user == null)
            return "redirect:/users/loginerror";
        return "boards/writeform";
    }

    // file upload시 같은 name의 파일이 여러개 전달 될 수 있기 때문에 배열로 받고 있다.
    @PostMapping
    public String write(@ModelAttribute Board board, @RequestParam("uploadFile") MultipartFile[] uploadFiles,  HttpSession session){
        Object userObj = session.getAttribute("login");
        if(userObj == null)
            return "redirect:/users/loginerror";
        // 사용자가 파일을 업로드 했을 경우
        List<BoardFile> boardFiles = new ArrayList<>();
        if(uploadFiles != null && uploadFiles.length > 0){
            for(MultipartFile uploadFile : uploadFiles){
                BoardFile boardFile = saveFile(uploadFile);
                boardFiles.add(boardFile); // db에 저장할 정보를 List에 저장한다.
            }
        }
        User user = (User)userObj;
        board.setUserId(user.getUserId());

        boardService.addBoard(board, boardFiles);

        return "redirect:/boards";
    }

    private BoardFile saveFile(MultipartFile uploadFile) {
        BoardFile boardFile = new BoardFile();
        boardFile.setMimeType(uploadFile.getContentType());
        boardFile.setFileLength((int)uploadFile.getSize());
        boardFile.setFileName(uploadFile.getOriginalFilename());
        String uuid = UUID.randomUUID().toString();
        // windows 사용자의 경우 c:\\tmp\\ 로 변경해줘야 한다. c:\tmp 폴더를 생성해야한다.
        //mac 사용자의 경우  \tmp\ 로 변경.
        String saveFileName = "c:\\tmp\\" + boardFile.getFileLength() + "_" + uuid;
        boardFile.setSaveFileName(saveFileName);
        // 파일을 복사한다.
        try(FileOutputStream fos = new FileOutputStream(saveFileName)) {
            IOUtils.copy(uploadFile.getInputStream(), fos);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return boardFile;
    }

    @GetMapping("/deleteform")
    public String deleteform(@RequestParam(name = "id") Long id, ModelMap modelMap, HttpSession session){
        Object user = session.getAttribute("login");
        if(user == null)
            return "redirect:/users/loginerror";

        Board board = boardService.getBoard(id);
        modelMap.addAttribute("board", board);

        return "boards/deleteform";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "id") Long id, HttpSession session){
        Object userObj = session.getAttribute("login");
        if(userObj == null)
            return "redirect:/users/loginerror";

        User user = (User)userObj;

        Board board = boardService.getBoard(id);
        if(board.getUserId().equals(user.getUserId())){ //게시물 글쓴이 ID와 로그인 ID가 같을 경우
            boardService.deleteBoard(id);
        }

        return "redirect:/boards";
    }

    @GetMapping("/updateform")
    public String updateform(@RequestParam(name = "id") Long id, ModelMap modelMap, HttpSession session){
        Object user = session.getAttribute("login");
        if(user == null)
            return "redirect:/users/loginerror";

        Board board = boardService.getBoard(id);
        modelMap.addAttribute("board", board);

        return "boards/updateform";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Board board, @RequestParam("uploadFile") MultipartFile[] uploadFiles, HttpSession session){
        Object userObj = session.getAttribute("login");
        if(userObj == null)
            return "redirect:/users/loginerror";
        User user = (User)userObj;
        board.setUserId(user.getUserId());


        // 파일을 저장한다.
        List<BoardFile> boardFiles = new ArrayList<>();
        if(uploadFiles != null && uploadFiles.length > 0){
            for(MultipartFile uploadFile : uploadFiles){
                BoardFile boardFile = saveFile(uploadFile);
                boardFiles.add(boardFile); // db에 저장할 정보를 List에 저장한다.
            }
        }

        if(board.getUserId().equals(user.getUserId())) { //게시물 글쓴이 ID와 로그인 ID가 같을 경우
            boardService.updateBoard(board, boardFiles);
        }

        return "redirect:/boards/" + board.getId(); // 수정된 글로 이동
    }
}
