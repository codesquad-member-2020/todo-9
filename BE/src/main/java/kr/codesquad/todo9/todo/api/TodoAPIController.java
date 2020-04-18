package kr.codesquad.todo9.todo.api;

import kr.codesquad.todo9.todo.domain.board.BoardDTO;
import kr.codesquad.todo9.todo.domain.column.Column;
import kr.codesquad.todo9.todo.service.todo.TodoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoAPIController {

    private static final Long defaultBoardId = 1L;

    private final TodoService todoService;

    public TodoAPIController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/board/{boardId}/column/list")
    public List<Column> showColumnList(@PathVariable Long boardId) {
        return todoService.getColumnsOfBoard(boardId);
    }

    @GetMapping("/column/list")
    public List<Column> showColumnList() {
        return showColumnList(defaultBoardId);
    }

    @GetMapping("/board/{boardId}")
    public BoardDTO showBoard(@PathVariable Long boardId) {
        return todoService.getSortedBoard(boardId);
    }

    @GetMapping("/board")
    public BoardDTO showBoard() {
        return showBoard(defaultBoardId);
    }

}
