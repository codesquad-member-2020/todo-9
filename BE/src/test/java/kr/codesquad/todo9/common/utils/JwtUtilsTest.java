package kr.codesquad.todo9.common.utils;

import kr.codesquad.todo9.todo.domain.user.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(JwtUtilsTest.class);

    @Test
    void jwtTest() {
        User user = new User(1L, "dion");
        log.debug("user: {}", user);
        String jws = JwtUtils.createJws(user);
        log.debug("jws: {}", jws);
        User userFromJws = new User(JwtUtils.getDataFromJws(jws));
        log.debug("userFromJws: {}", userFromJws);
        assertThat(userFromJws).isEqualToComparingFieldByField(user);
    }
}
