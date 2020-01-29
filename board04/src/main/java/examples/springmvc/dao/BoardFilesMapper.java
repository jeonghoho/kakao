package examples.springmvc.dao;

import examples.springmvc.dto.Board;
import examples.springmvc.dto.BoardFile;
import examples.springmvc.dto.SearchCondition;
import examples.springmvc.mapper.Mapper;

import java.util.List;

@Mapper
public interface BoardFilesMapper {
    public Long addBoardFile(BoardFile boardFile);
    public BoardFile getBoardFile(Long boardFileId);
    void deleteBoardFiles(Long boardId);
    public List<BoardFile> getBoardFiles(Long boardId);
}

