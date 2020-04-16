package kr.codesquad.todo9.todo.api;

import kr.codesquad.todo9.common.error.exception.BoardNotFoundException;
import kr.codesquad.todo9.todo.domain.board.Board;
import kr.codesquad.todo9.todo.domain.column.Column;
import kr.codesquad.todo9.todo.domain.board.BoardDTO;
import kr.codesquad.todo9.todo.domain.column.ColumnDTO;
import kr.codesquad.todo9.todo.domain.log.LogDTO;
import kr.codesquad.todo9.todo.domain.board.BoardRepository;
import kr.codesquad.todo9.todo.domain.card.CardRepository;
import kr.codesquad.todo9.todo.domain.log.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoAPIController {

    private static final Logger log = LoggerFactory.getLogger(TodoAPIController.class);
    private static final Long defaultBoardId = 1L;

    private final BoardRepository boardRepository;
    private final LogRepository logRepository;
    private final CardRepository cardRepository;

    public TodoAPIController(BoardRepository boardRepository,
                             LogRepository logRepository,
                             CardRepository cardRepository) {
        this.boardRepository = boardRepository;
        this.logRepository = logRepository;
        this.cardRepository = cardRepository;
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
        List<ColumnDTO> columns = new ArrayList<>();
        for (Column column : board.getColumns()) {
            ColumnDTO columnDTO = new ColumnDTO(column);
            List<CardDTO> cardList = new ArrayList<>();
            for (CardDTO cardDTO : cardRepository.getCardList(columnDTO.getId())) {
                if (!cardDTO.getArchived()) {
                    cardList.add(cardDTO);
                }
            }
            columnDTO.setCards(cardList);
            columns.add(columnDTO);
        }
        List<LogDTO> logs = logRepository.getLogDTOList(defaultBoardId);
        BoardDTO boardDTO = new BoardDTO(board, columns, logs);
        log.debug("boardDTO: {}", boardDTO);
        return boardDTO;
    }

    @GetMapping("/board")
    public BoardDTO showBoard() {
        return showBoard(defaultBoardId);
    }

}
