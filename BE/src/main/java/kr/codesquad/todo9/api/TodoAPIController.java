package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.*;
import kr.codesquad.todo9.error.exception.BoardNotFoundException;
import kr.codesquad.todo9.error.exception.UserNotFoundException;
import kr.codesquad.todo9.repository.BoardRepository;
import kr.codesquad.todo9.repository.UserRepository;
import kr.codesquad.todo9.responseobjects.Result;
import kr.codesquad.todo9.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("user/{name}")
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

        List<Card> cards = column.getCards();
        Collections.sort(cards);
        log.debug("cards: {}", cards);
        return cards;
    }

    @GetMapping("/column/{boardKey}/card/list")
    public List<Card> showCardListOfColumn(@PathVariable int boardKey) {
        return showCardListOfColumnOfBoard(defaultBoardId, boardKey);
    }

    @PostMapping("/board/{boardId}/column/{boardKey}/card/{contents}")
    @Transactional
    public Result addCard(@PathVariable Long boardId, @PathVariable int boardKey, @PathVariable String contents) {
        User user = userRepository.findById(defaultUserId).orElseThrow(UserNotFoundException::new);
        log.debug("firstUser: {}", user);

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        board.addCard(boardKey, contents, user);
        boardRepository.save(board);
        log.debug("save after board: {}", board);

        board.addLog("create", "card", user, contents, boardKey);
        boardRepository.save(board);
        log.debug("save log after board: {}", board);

        return new Result(true, "success");
    }

    @PostMapping("/column/{boardKey}/card/{contents}")
    @Transactional
    public Result addCard(@PathVariable int boardKey, @PathVariable String contents) {
        return addCard(defaultBoardId, boardKey, contents);
    }

    @GetMapping("/board/{boardId}/column/list")
    public List<Column> showColumnList(@PathVariable Long boardId) {
        List<Column> columns = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).getColumns();
        for (Column column : columns) {
            Collections.sort(column.getCards());
        }
        return columns;
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
        List<Column> columns = board.getColumns();
        for (Column column : columns) {
            Collections.sort(column.getCards());
        }
        Collections.reverse(board.getLogs());
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
            throw new RuntimeException("해당하는 로그를 찾지 못했습니다.");
        }
        return logs.get(boardKey - 1);
    }

    @GetMapping("/log/{boardKey}")
    public Log showLog(@PathVariable int boardKey) {
        return showLog(defaultBoardId, boardKey);
    }

    @PutMapping("/board/{boardId}/column/{boardKey}/card/{columnKey}")
    public Result editCard(@PathVariable Long boardId,
                           @PathVariable int boardKey,
                           @PathVariable int columnKey,
                           @RequestBody String contents) {
        User user = userRepository.findById(defaultUserId).orElseThrow(UserNotFoundException::new);
        log.debug("firstUser: {}", user);

        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        log.debug("board: {}", board);

        board.updateCard(boardKey, columnKey, contents, user);
        boardRepository.save(board);
        log.debug("save after board: {}", board);

        return new Result(true, "success");
    }

    @PutMapping("/column/{boardKey}/card/{columnKey}")
    public Result editCard(@PathVariable int boardKey, @PathVariable int columnKey, @RequestBody String contents) {
        return this.editCard(defaultBoardId, boardKey, columnKey, contents);
    }
}
