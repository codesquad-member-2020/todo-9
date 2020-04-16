package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.Board;
import kr.codesquad.todo9.domain.Column;
import kr.codesquad.todo9.dto.BoardDTO;
import kr.codesquad.todo9.dto.LogDTO;
import kr.codesquad.todo9.error.exception.BoardNotFoundException;
import kr.codesquad.todo9.repository.BoardRepository;
import kr.codesquad.todo9.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoAPIController {

    private static final Logger log = LoggerFactory.getLogger(TodoAPIController.class);
    private static final Long defaultBoardId = 1L;

    private final BoardRepository boardRepository;
    private final LogRepository logRepository;

    public TodoAPIController(BoardRepository boardRepository, LogRepository logRepository) {
        this.boardRepository = boardRepository;
        this.logRepository = logRepository;
    }

    @GetMapping("/board/{boardId}/column/list")
    public List<Column> showColumnList(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).sortBoard();
        log.debug("board: {}", board);
        return board.getColumns();
    }

    @GetMapping("/column/list")
    public List<Column> showColumnList() {
        return showColumnList(defaultBoardId);
    }

    @GetMapping("/board/{boardId}")
    public BoardDTO showBoard(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).sortBoard();
        List<LogDTO> logs = logRepository.getLogDTOList(defaultBoardId);
        BoardDTO boardDTO = new BoardDTO(board, logs);
        log.debug("boardDTO: {}", boardDTO);
        return boardDTO;
    }

    @GetMapping("/board")
    public BoardDTO showBoard() {
        return showBoard(defaultBoardId);
    }

}
