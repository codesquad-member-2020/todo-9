package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoAPIController {

    private static final Logger log = LoggerFactory.getLogger(TodoAPIController.class);

    private Board board;

    public TodoAPIController() {
        // User
        User newUser = new User(1L, "newUser");

        // todo Cards
        Card firstCard = new Card(1L, "안녕하세요~~", newUser.getId());
        Card secondCard = new Card(2L, "asdf", newUser.getId());
        firstCard.setAfterCardId(2L);
        secondCard.setBeforeCardId(1L);

        List<Card> cards = new ArrayList<>();
        cards.add(firstCard);
        cards.add(secondCard);

        // columns
        Column todo = new Column(1L, "todo");
        Column doing = new Column(2L, "doing");
        Column done = new Column(3L, "done");
        todo.setOrder(1);
        todo.setCards(cards);
        doing.setOrder(2);
        done.setOrder(3);

        List<Column> columns = new ArrayList<>();
        columns.add(todo);
        columns.add(doing);
        columns.add(done);

        // logs
        Log firstLog = new Log(1L, "create", null, null, null, 1L, 1L, 1L);
        Log secondLog = new Log(2L, "create", null, null, null, 2L, 1L, 1L);
        Log thirdLog = new Log(3L, "create", null, null, null, 3L, 1L, 1L);
        Log fourthLog = new Log(4L, "create", null, 1L, 1L, 1L, 1L, 1L);
        Log fifthLog = new Log(5L, "create", null, 2L, 1L, 1L, 1L, 1L);

        List<Log> logs = new ArrayList<>();
        logs.add(firstLog);
        logs.add(secondLog);
        logs.add(thirdLog);
        logs.add(fourthLog);
        logs.add(fifthLog);

        // board
        Board board = new Board(1L, "To Do 프로젝트");
        board.setColumns(columns);
        board.setLogs(logs);

        this.board = board;
    }

    @GetMapping("/board")
    public Board showBoard() {
        return board;
    }
}
