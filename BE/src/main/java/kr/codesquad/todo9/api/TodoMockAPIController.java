package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.Board;
import kr.codesquad.todo9.domain.Card;
import kr.codesquad.todo9.domain.Column;
import kr.codesquad.todo9.domain.User;
import kr.codesquad.todo9.responseobjects.Result;
import kr.codesquad.todo9.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoMockAPIController {

    private static final Logger log = LoggerFactory.getLogger(TodoMockAPIController.class);

    private Board board;

    public TodoMockAPIController() {
        // User
        User newUser = new User(1L, "newUser");

        Card firstCard = new Card(1L, "안녕하세요~~", newUser);
        Card secondCard = new Card(2L, "asdf", newUser);

        List<Card> cards = new ArrayList<>();
        cards.add(firstCard);
        cards.add(secondCard);

        // columns
        Column todo = new Column(1L, "todo");
        Column doing = new Column(2L, "doing");
        Column done = new Column(3L, "done");
        todo.setColumnOrder(1);
        todo.setCards(cards);
        doing.setColumnOrder(2);
        done.setColumnOrder(3);

        List<Column> columns = new ArrayList<>();
        columns.add(todo);
        columns.add(doing);
        columns.add(done);

        // board
        Board board = new Board(1L, "To Do 프로젝트");
        board.setColumns(columns);

        this.board = board;
    }

    @GetMapping("/board")
    public Board showBoard() {
        return board;
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(HttpServletResponse httpServletResponse) {
        Result result = new Result(true, "success");
        User newUser = new User(1L, "todo9");
        String jws = JwtUtils.createJws(newUser);
        log.debug("newUser: {}", newUser);
        log.debug("jws: {}", jws);

        Cookie jwsCookie = new Cookie("jws", jws);
        httpServletResponse.addCookie(jwsCookie);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
