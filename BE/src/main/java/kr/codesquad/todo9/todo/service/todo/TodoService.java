package kr.codesquad.todo9.todo.service.todo;

import kr.codesquad.todo9.todo.domain.board.BoardDTO;
import kr.codesquad.todo9.todo.domain.column.Column;

import java.util.List;

public interface TodoService {

    List<Column> getColumnsOfBoard(Long boardId);

    BoardDTO getSortedBoard(Long boardId);

}
