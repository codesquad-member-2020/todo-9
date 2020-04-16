package kr.codesquad.todo9.todo.service.user;

import kr.codesquad.todo9.common.error.exception.UserNotFoundException;
import kr.codesquad.todo9.common.utils.JwtUtils;
import kr.codesquad.todo9.todo.domain.user.User;
import kr.codesquad.todo9.todo.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Long defaultUserId = 1L;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Cookie login() {
        User user = userRepository.findById(defaultUserId).orElseThrow(UserNotFoundException::new);
        String jws = JwtUtils.createJws(user);
        log.debug("user: {}", user);
        log.debug("jws: {}", jws);

        return new Cookie("jws", jws);
    }

    @Override
    public Iterable<User> getUserList() {
        return userRepository.findAll();
    }
}
