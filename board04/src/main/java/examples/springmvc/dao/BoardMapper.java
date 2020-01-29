package examples.springmvc.dao;

import examples.springmvc.dto.Board;
import examples.springmvc.dto.SearchCondition;
import examples.springmvc.mapper.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    public Long addBoard(Board board);
    public void plusOneBoardCount(Long id);
    public Board getBoard(Long id);
    public List<Board> getBoards(SearchCondition searchCondition);
    public int getBoardCount(SearchCondition searchCondition);
    void deleteBoard(Long id);
    void updateBoard(Board board);
}

