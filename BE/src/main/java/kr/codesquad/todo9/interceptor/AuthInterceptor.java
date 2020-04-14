package kr.codesquad.todo9.interceptor;

import kr.codesquad.todo9.domain.User;
import kr.codesquad.todo9.error.exception.LoginRequiredException;
import kr.codesquad.todo9.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpCookie;
import java.util.List;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        List<HttpCookie> cookies = HttpCookie.parse(request.getHeader("Cookie"));
        Optional<HttpCookie> jwsCookie = Optional.empty();
        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equals("jws")) {
                jwsCookie = Optional.of(cookie);
            }
        }

        User user = new User(JwtUtils.getDataFromJws(jwsCookie.orElseThrow(LoginRequiredException::new).getValue()));
        request.setAttribute("user", user);
        log.debug("user: {}", user);
        log.debug("cookie: {}", cookies);
        return true;
    }
}
