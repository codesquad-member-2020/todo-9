package kr.codesquad.todo9.todo.api;

import kr.codesquad.todo9.common.error.exception.UserNotFoundException;
import kr.codesquad.todo9.common.utils.JwtUtils;
import kr.codesquad.todo9.todo.domain.user.User;
import kr.codesquad.todo9.todo.domain.user.UserRepository;
import kr.codesquad.todo9.todo.responseobject.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserAPIController {

    private static final Logger log = LoggerFactory.getLogger(UserAPIController.class);
    private static final Long defaultUserId = 1L;

    private final UserRepository userRepository;

    public UserAPIController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
