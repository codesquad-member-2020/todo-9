package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.User;
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

    public TodoAPIController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
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

}
