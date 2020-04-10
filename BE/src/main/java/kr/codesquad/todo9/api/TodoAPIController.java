package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.Card;
import kr.codesquad.todo9.domain.User;
import kr.codesquad.todo9.dto.CardDTO;
import kr.codesquad.todo9.repository.CardRepository;
import kr.codesquad.todo9.repository.UserRepository;
import kr.codesquad.todo9.responseobjects.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mock")
public class TodoAPIController {

    private static final Logger log = LoggerFactory.getLogger(TodoAPIController.class);

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public TodoAPIController(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
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

    @GetMapping("/card/list")
    public Iterable<CardDTO> showCardList() {
        return cardRepository.findAllCards();
    }

    @PostMapping("/card/{contents}")
    public Result addCard(@PathVariable String contents) {
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        log.debug("firstUser: {}", user);

        Card card = new Card();
        card.setContents(contents);
        card.setCreatedUserId(user.getId());
        card.setUpdatedUserId(user.getId());
        card.setCardOrder(cardRepository.getNextOrderOfColumn(1L));
        card.setColumnKey(1L);
        log.debug("new Card: {}", card);

        card = cardRepository.save(card);
        log.debug("new Card: {}", card);

        return new Result(true, "success");
    }

}
