package examples.springmvc.service;

import examples.springmvc.dao.BoardFilesMapper;
import examples.springmvc.dao.BoardMapper;
import examples.springmvc.dto.Board;
import examples.springmvc.dto.BoardFile;
import examples.springmvc.dto.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BoardFilesMapper boardFilesMapper;

    @Transactional(readOnly = false)
    public Long addBoard(Board board, List<BoardFile> boardFiles){
        Long count = boardMapper.addBoard(board);
        if(boardFiles != null){
            for(BoardFile boardFile : boardFiles){
                boardFile.setBoardId(board.getId()); //DB에 저장된 후 자동 생성된 id를 파일에 설정한다.
                boardFilesMapper.addBoardFile(boardFile);
            }
        }
        return count;
    }

    @Transactional
    public List<BoardFile> getBoardFiles(Long boardId){
        return boardFilesMapper.getBoardFiles(boardId);
    }

    @Transactional
    public List<Board> getBoards(SearchCondition searchCondition){
        return boardMapper.getBoards(searchCondition);
    }

    @Transactional(readOnly = false)
    public Board getBoard(Long id){
        boardMapper.plusOneBoardCount(id); // 조회수를 증가시킨다.
        return boardMapper.getBoard(id);
    }

    @Transactional
    public int getBoardCount(SearchCondition searchCondition) {
        return boardMapper.getBoardCount(searchCondition);
    }

    @Transactional(readOnly = false)
    public void deleteBoard(Long id) {
        boardMapper.deleteBoard(id);

        // 파일을 디스크에서 삭제한다.
        List<BoardFile> boardFileList = boardFilesMapper.getBoardFiles(id); //파일 목록을 읽어옴
        if(boardFileList != null) {
            for (BoardFile boardFile : boardFileList) {
                try {
                    Files.delete(new File(boardFile.getSaveFileName()).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Transactional(readOnly = false)
    public void updateBoard(Board board, List<BoardFile>  boardFiles) {
        List<BoardFile> boardFileList = boardFilesMapper.getBoardFiles(board.getId()); //파일 목록을 읽어옴
        boardFilesMapper.deleteBoardFiles(board.getId()); // 기존 파일을 삭제
        boardMapper.updateBoard(board);

        // 파알정보, 글 삭제가 되면 실제로 파일을 삭제한다.
        for(BoardFile boardFile : boardFileList){
            try {
                Files.delete(new File(boardFile.getSaveFileName()).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 새로운 파일 정보를 추가한다.
        if(boardFiles != null) {
            for (BoardFile boardFile : boardFiles) {
                boardFile.setBoardId(board.getId()); // 수정되는 게시판 id로 설정한다.
                boardFilesMapper.addBoardFile(boardFile);
            }
        }
    }

    @Transactional
    public BoardFile getBoardFile(Long boardFileId) {
        return boardFilesMapper.getBoardFile(boardFileId);
    }
}
