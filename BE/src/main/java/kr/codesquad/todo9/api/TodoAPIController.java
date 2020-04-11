package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.Card;
import kr.codesquad.todo9.domain.Column;
import kr.codesquad.todo9.domain.Log;
import kr.codesquad.todo9.domain.User;
import kr.codesquad.todo9.repository.ColumnRepository;
import kr.codesquad.todo9.repository.LogRepository;
import kr.codesquad.todo9.repository.UserRepository;
import kr.codesquad.todo9.responseobjects.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/mock")
public class TodoAPIController {

    private static final Logger log = LoggerFactory.getLogger(TodoAPIController.class);

    private final UserRepository userRepository;
    private final ColumnRepository columnRepository;
    private final LogRepository logRepository;

    public TodoAPIController(UserRepository userRepository,
                             ColumnRepository columnRepository,
                             LogRepository logRepository) {
        this.userRepository = userRepository;
        this.columnRepository = columnRepository;
        this.logRepository = logRepository;
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

    @GetMapping("/column/{columnId}/card/list")
    public List<Card> showCardListOfColumn(@PathVariable Long columnId) {
        Column column = columnRepository.findById(columnId).orElseThrow(RuntimeException::new);
        log.debug("column: {}", column);

        List<Card> cards = column.getCards();
        Collections.sort(cards);
        log.debug("cards: {}", cards);
        return cards;
    }

    @PostMapping("/column/{columnId}/card/{contents}")
    public Result addCardIntoColumn(@PathVariable Long columnId, @PathVariable String contents) {
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        log.debug("firstUser: {}", user);

        Column column = columnRepository.findById(columnId).orElseThrow(RuntimeException::new);
        log.debug("column: {}", column);

        column.addCard(contents, user);
        column = columnRepository.save(column);
        log.debug("save after column: {}", column);

        return new Result(true, "success");
    }

    @GetMapping("/column/list")
    public Iterable<Column> showColumnList() {
        Iterable<Column> columns = columnRepository.findAll();
        for (Column column : columns) {
            Collections.sort(column.getCards());
        }
        log.debug("columns: {}", columns);
        return columns;
    }

    @GetMapping("/log/list")
    public Iterable<Log> showLogList() {
        return logRepository.findAll();
    }

}
