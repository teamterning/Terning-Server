package org.terning.terningserver.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.terning.terningserver.config.ValueConfig;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.util.Base64.getEncoder;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ValueConfig valueConfig;

    public String generateToken(Authentication authentication, long expiration) {
        return Jwts.builder()
                .setHeaderParam(TYPE, JWT_TYPE)
                .setClaims(generateClaims(authentication))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    private Claims generateClaims(Authentication authentication) {
        val claims = Jwts.claims();
        claims.put("userId", authentication.getPrincipal());
        return claims;
    }

    private SecretKey getSigningKey() {
        val encodedKey = getEncoder().encodeToString(valueConfig.getSecretKey().getBytes());
        return hmacShaKeyFor(encodedKey.getBytes());
    }
}
