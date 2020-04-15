package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.*;
import kr.codesquad.todo9.error.exception.BoardNotFoundException;
import kr.codesquad.todo9.error.exception.LogNotFoundException;
import kr.codesquad.todo9.error.exception.UserNotFoundException;
import kr.codesquad.todo9.repository.BoardRepository;
import kr.codesquad.todo9.repository.UserRepository;
import kr.codesquad.todo9.requestobject.ContentsObject;
import kr.codesquad.todo9.requestobject.MoveCardObject;
import kr.codesquad.todo9.responseobject.Result;
import kr.codesquad.todo9.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class TodoAPIController {

    private static final Logger log = LoggerFactory.getLogger(TodoAPIController.class);
    private static final Long defaultBoardId = 1L;
    private static final Long defaultUserId = 1L;

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public TodoAPIController(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(HttpServletResponse httpServletResponse) {
        Result result = new Result(true, "success");
        User user = userRepository.findById(defaultUserId).orElseThrow(UserNotFoundException::new);
        String jws = JwtUtils.createJws(user);
        log.debug("user: {}", user);
        log.debug("jws: {}", jws);

        Cookie jwsCookie = new Cookie("jws", jws);
        httpServletResponse.addCookie(jwsCookie);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/user/list")
    public Iterable<User> showUserList() {
        return userRepository.findAll();
    }

    @PostMapping("/user/{name}")
    public Result addUser(@PathVariable String name) {
        User newUser = new User();
        newUser.setUsername(name);
        log.debug("newUser: {}", newUser);

        newUser = userRepository.save(newUser);
        log.debug("newUser: {}", newUser);

        return new Result(true, "success");
    }

    @GetMapping("/board/{boardId}/column/{boardKey}/card/list")
    public List<Card> showCardListOfColumnOfBoard(@PathVariable Long boardId, @PathVariable int boardKey) {
        Column column = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).getColumns().get(boardKey);
        log.debug("column: {}", column);

        List<Card> cards = new ArrayList<>();
        for (Card card : column.getCards()) {
            if (!card.getArchived()) {
                cards.add(card);
            }
        }
        Collections.sort(cards);
        log.debug("cards: {}", cards);
        return cards;
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
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        board.sortBoard();
        return board.getColumns();
    }

    @GetMapping("/column/list")
    public List<Column> showColumnList() {
        return showColumnList(defaultBoardId);
    }

    @GetMapping("/board/{boardId}/log/list")
    public List<Log> showLogList(@PathVariable Long boardId) {
        List<Log> logs = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).getLogs();
        Collections.reverse(logs);
        return logs;
    }

    @GetMapping("/log/list")
    public List<Log> showLogList() {
        return showLogList(defaultBoardId);
    }

    @GetMapping("/board/{boardId}")
    public Board showBoard(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        board.sortBoard();
        return board;
    }

    @GetMapping("/board")
    public Board showBoard() {
        return showBoard(defaultBoardId);
    }

    @GetMapping("/board/{boardId}/log/{boardKey}")
    public Log showLog(@PathVariable Long boardId, @PathVariable int boardKey) {
        List<Log> logs = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).getLogs();
        if (logs.size() < boardKey) {
            throw new LogNotFoundException();
        }
        return logs.get(boardKey - 1);
    }

    @GetMapping("/log/{boardKey}")
    public Log showLog(@PathVariable int boardKey) {
        return showLog(defaultBoardId, boardKey);
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
