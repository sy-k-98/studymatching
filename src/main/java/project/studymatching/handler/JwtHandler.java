package project.studymatching.handler;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtHandler {

    private String type = "";

    public String createToken(String key, Map<String, Object> privateClaims, long maxAgeSeconds) {
        Date now = new Date();
        return type + Jwts.builder()
                .addClaims(privateClaims)
                .addClaims(Map.of(Claims.ISSUED_AT, now, Claims.EXPIRATION, new Date(now.getTime() + maxAgeSeconds * 1000L)))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }

    public Optional<Claims> parse(String key, String token) {
        try {
            return Optional.of(Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(untype(token)).getBody());
        } catch (JwtException e) {
            return Optional.empty();
        }
    }

    private String untype(String token) {
        return token.substring(type.length());
    }
}
