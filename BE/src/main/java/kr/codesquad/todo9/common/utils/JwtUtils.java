package kr.codesquad.todo9.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.codesquad.todo9.common.error.exception.LoginRequiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtUtils {

    private JwtUtils() {}

    private static final String DATA = "data";
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String createJws(Object data) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put(DATA, data);

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .signWith(key)
                .compact();
    }

    public static Map<String, Object> getDataFromJws(String jws) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder()
                         .setSigningKey(key)
                         .build()
                         .parseClaimsJws(jws);
            return (LinkedHashMap<String, Object>) claims.getBody().get(DATA);
        } catch (JwtException ex) {
            log.error("인증되지 않은 jwt token입니다. jws: {}", jws);
            // Custom Exception Unauthorized Exception
            throw new LoginRequiredException("인증되지 않은 jwt token입니다.");
        }
    }
}
