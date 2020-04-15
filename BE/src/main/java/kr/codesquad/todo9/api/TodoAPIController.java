package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.*;
import kr.codesquad.todo9.error.exception.BoardNotFoundException;
import kr.codesquad.todo9.repository.BoardRepository;
import kr.codesquad.todo9.requestobject.ContentsObject;
import kr.codesquad.todo9.requestobject.MoveCardObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoAPIController {

    private static final Logger log = LoggerFactory.getLogger(TodoAPIController.class);
    private static final Long defaultBoardId = 1L;

    private final BoardRepository boardRepository;

    public TodoAPIController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/board/{boardId}/column/{boardKey}/card/list")
    public List<Card> showCardListOfColumnOfBoard(@PathVariable Long boardId, @PathVariable int boardKey) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).sortBoard();
        log.debug("board: {}", board);
        return board.getColumns().get(boardKey).getCards();
    }

    @GetMapping("/column/{boardKey}/card/list")
    public List<Card> showCardListOfColumn(@PathVariable int boardKey) {
        return showCardListOfColumnOfBoard(defaultBoardId, boardKey);
    }

    @PostMapping("/board/{boardId}/column/{boardKey}/card")
    @Transactional
    public Log addCard(HttpServletRequest request,
                       @PathVariable Long boardId,
                       @PathVariable int boardKey,
                       @RequestBody @Valid ContentsObject contentsObject) {
        User user = (User) request.getAttribute("user");
        log.debug("firstUser: {}", user);

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        String contents = contentsObject.getContents();

        board.addCard(boardKey, contents, user);
        boardRepository.save(board);
        log.debug("save after board: {}", board);

        board.addLog("create", "card", user, contents, boardKey);
        board = boardRepository.save(board);
        log.debug("save log after board: {}", board);
        return board.getLastLog();
    }

    @PostMapping("/column/{boardKey}/card")
    @Transactional
    public Log addCard(HttpServletRequest request,
                       @PathVariable int boardKey,
                       @RequestBody @Valid ContentsObject contentsObject) {
        return addCard(request, defaultBoardId, boardKey, contentsObject);
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
    public Board showBoard(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).sortBoard();
        log.debug("board: {}", board);
        return board;
    }

    @GetMapping("/board")
    public Board showBoard() {
        return showBoard(defaultBoardId);
    }

    @PutMapping("/board/{boardId}/column/{boardKey}/card/{columnKey}")
    public Log editCard(HttpServletRequest request,
                        @PathVariable Long boardId,
                        @PathVariable int boardKey,
                        @PathVariable int columnKey,
                        @RequestBody @Valid ContentsObject contentsObject) {
        User user = (User) request.getAttribute("user");
        log.debug("firstUser: {}", user);

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        board.updateCard(boardKey, columnKey, contentsObject.getContents(), user);
        board = boardRepository.save(board);
        log.debug("save after board: {}", board);
        return board.getLastLog();
    }

    @PutMapping("/column/{boardKey}/card/{columnKey}")
    public Log editCard(HttpServletRequest request,
                        @PathVariable int boardKey,
                        @PathVariable int columnKey,
                        @RequestBody @Valid ContentsObject contentsObject) {
        return this.editCard(request, defaultBoardId, boardKey, columnKey, contentsObject);
    }

    @DeleteMapping("/board/{boardId}/column/{boardKey}/card/{columnKey}")
    public Log deleteCard(HttpServletRequest request,
                          @PathVariable Long boardId,
                          @PathVariable int boardKey,
                          @PathVariable int columnKey) {
        User user = (User) request.getAttribute("user");
        log.debug("firstUser: {}", user);

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        board.deleteCard(boardKey, columnKey, user);
        board = boardRepository.save(board);
        log.debug("save after board: {}", board);
        return board.getLastLog();
    }

    @DeleteMapping("/column/{boardKey}/card/{columnKey}")
    public Log deleteCard(HttpServletRequest request, @PathVariable int boardKey, @PathVariable int columnKey) {
        return deleteCard(request, defaultBoardId, boardKey, columnKey);
    }

    @PatchMapping("/board/{boardId}/column/{boardKey}/card/{columnKey}")
    public Log moveCard(HttpServletRequest request,
                        @PathVariable Long boardId,
                        @PathVariable int boardKey,
                        @PathVariable int columnKey,
                        @RequestBody MoveCardObject moveCardObject) {
        User user = (User) request.getAttribute("user");
        log.debug("firstUser: {}", user);

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        board.moveCard(boardKey, columnKey, user, moveCardObject);
        board = boardRepository.save(board);
        log.debug("save after board: {}", board);
        return board.getLastLog();
    }

    @PatchMapping("/column/{boardKey}/card/{columnKey}")
    public Log moveCard(HttpServletRequest request,
                        @PathVariable int boardKey,
                        @PathVariable int columnKey,
                        @RequestBody MoveCardObject moveCardObject) {
        return moveCard(request, defaultBoardId, boardKey, columnKey, moveCardObject);
    }
}
