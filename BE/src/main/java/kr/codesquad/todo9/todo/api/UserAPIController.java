package kr.codesquad.todo9.todo.api;

import kr.codesquad.todo9.todo.domain.user.User;
import kr.codesquad.todo9.todo.responseobject.Result;
import kr.codesquad.todo9.todo.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserAPIController {

    private final UserService userService;

    public UserAPIController(UserService userService) {this.userService = userService;}

    @PostMapping("/login")
    public ResponseEntity<Result> login(HttpServletResponse httpServletResponse) {
        Result result = new Result(true, "success");
        httpServletResponse.addCookie(userService.login());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/user/list")
    public Iterable<User> showUserList() {
        return userService.getUserList();
    }
}
