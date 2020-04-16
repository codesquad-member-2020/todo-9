package kr.codesquad.todo9.todo.api;

import kr.codesquad.todo9.common.error.exception.BoardNotFoundException;
import kr.codesquad.todo9.todo.domain.board.Board;
import kr.codesquad.todo9.todo.domain.card.Card;
import kr.codesquad.todo9.todo.domain.user.User;
import kr.codesquad.todo9.todo.domain.log.LogDTO;
import kr.codesquad.todo9.todo.domain.board.BoardRepository;
import kr.codesquad.todo9.todo.domain.log.LogRepository;
import kr.codesquad.todo9.todo.requestobject.ContentsObject;
import kr.codesquad.todo9.todo.requestobject.MoveCardObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CardAPIController {

    private static final Logger log = LoggerFactory.getLogger(CardAPIController.class);
    private static final Long DEFAULT_BOARD_ID = 1L;

    private final BoardRepository boardRepository;
    private final LogRepository logRepository;

    public CardAPIController(BoardRepository boardRepository, LogRepository logRepository) {
        this.boardRepository = boardRepository;
        this.logRepository = logRepository;
    }

    @GetMapping("/board/{boardId}/column/{boardKey}/card/list")
    public List<Card> showCardListOfColumnOfBoard(@PathVariable Long boardId, @PathVariable int boardKey) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).sortBoard();
        log.debug("board: {}", board);
        return board.getColumns().get(boardKey).getCards();
    }

    @GetMapping("/column/{boardKey}/card/list")
    public List<Card> showCardListOfColumn(@PathVariable int boardKey) {
        return showCardListOfColumnOfBoard(DEFAULT_BOARD_ID, boardKey);
    }

    @PostMapping("/board/{boardId}/column/{boardKey}/card")
    @Transactional
    public LogDTO addCard(HttpServletRequest request,
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
        return logRepository.getLogDTO(board.getId(), board.getLastLog().getId());
    }

    @PostMapping("/column/{boardKey}/card")
    @Transactional
    public LogDTO addCard(HttpServletRequest request,
                         @PathVariable int boardKey,
                         @RequestBody @Valid ContentsObject contentsObject) {
        return addCard(request, DEFAULT_BOARD_ID, boardKey, contentsObject);
    }

    @PutMapping("/board/{boardId}/column/{boardKey}/card/{columnKey}")
    public LogDTO editCard(HttpServletRequest request,
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
        return logRepository.getLogDTO(board.getId(), board.getLastLog().getId());
    }

    @PutMapping("/column/{boardKey}/card/{columnKey}")
    public LogDTO editCard(HttpServletRequest request,
                          @PathVariable int boardKey,
                          @PathVariable int columnKey,
                          @RequestBody @Valid ContentsObject contentsObject) {
        return this.editCard(request, DEFAULT_BOARD_ID, boardKey, columnKey, contentsObject);
    }

    @DeleteMapping("/board/{boardId}/column/{boardKey}/card/{columnKey}")
    public LogDTO deleteCard(HttpServletRequest request,
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
        return logRepository.getLogDTO(board.getId(), board.getLastLog().getId());
    }

    @DeleteMapping("/column/{boardKey}/card/{columnKey}")
    public LogDTO deleteCard(HttpServletRequest request, @PathVariable int boardKey, @PathVariable int columnKey) {
        return deleteCard(request, DEFAULT_BOARD_ID, boardKey, columnKey);
    }

    @PatchMapping("/board/{boardId}/column/{boardKey}/card/{columnKey}")
    public LogDTO moveCard(HttpServletRequest request,
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
        return logRepository.getLogDTO(board.getId(), board.getLastLog().getId());
    }

    @PatchMapping("/column/{boardKey}/card/{columnKey}")
    public LogDTO moveCard(HttpServletRequest request,
                           @PathVariable int boardKey,
                           @PathVariable int columnKey,
                           @RequestBody MoveCardObject moveCardObject) {
        return moveCard(request, DEFAULT_BOARD_ID, boardKey, columnKey, moveCardObject);
    }
}
