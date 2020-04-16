package kr.codesquad.todo9.todo.service.user;

import kr.codesquad.todo9.todo.domain.user.User;

import javax.servlet.http.Cookie;

public interface UserService {

    Cookie login();

    Iterable<User> getUserList();

}
