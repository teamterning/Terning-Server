package org.terning.terningserver.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.terning.terningserver.auth.dto.Token;
import org.terning.terningserver.auth.jwt.exception.JwtErrorCode;
import org.terning.terningserver.auth.jwt.exception.JwtException;
import org.terning.terningserver.common.config.ValueConfig;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String USER_ID_CLAIM = "userId";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final ValueConfig valueConfig;

    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(valueConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public Token generateTokens(Long userId) {
        String accessToken = generateToken(userId, valueConfig.getAccessTokenExpired());
        String refreshToken = generateToken(userId, valueConfig.getRefreshTokenExpired());
        return new Token(accessToken, refreshToken);
    }

    public Token generateAccessToken(Long userId) {
        String accessToken = generateToken(userId, valueConfig.getAccessTokenExpired());
        return new Token(accessToken, null);
    }

    public Long getUserIdFrom(String authorizationHeader) {
        String token = resolveToken(authorizationHeader);
        Claims claims = parseClaims(token);
        Object userIdClaim = claims.get(USER_ID_CLAIM);

        if (userIdClaim instanceof Number) {
            return ((Number) userIdClaim).longValue();
        }
        throw new JwtException(JwtErrorCode.INVALID_USER_ID_TYPE);
    }

    public String resolveToken(String rawToken) {
        if (rawToken != null && rawToken.startsWith(TOKEN_PREFIX)) {
            return rawToken.substring(TOKEN_PREFIX.length());
        }
        throw new JwtException(JwtErrorCode.TOKEN_NOT_FOUND);
    }

    private String generateToken(Long userId, long expiration) {
        Claims claims = Jwts.claims();
        claims.put(USER_ID_CLAIM, userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.secretKey)
                .compact();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(this.secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            handleJwtException(e);
            throw new JwtException(JwtErrorCode.UNEXPECTED_ERROR);
        }
    }

    private void handleJwtException(Exception e) {
        if (e instanceof ExpiredJwtException) {
            throw new JwtException(JwtErrorCode.EXPIRED_TOKEN);
        }
        if (e instanceof SecurityException) {
            throw new JwtException(JwtErrorCode.SIGNATURE_ERROR);
        }
        if (e instanceof MalformedJwtException) {
            throw new JwtException(JwtErrorCode.MALFORMED_TOKEN);
        }
        if (e instanceof UnsupportedJwtException) {
            throw new JwtException(JwtErrorCode.UNSUPPORTED_TOKEN);
        }
        if (e instanceof IllegalArgumentException) {
            throw new JwtException(JwtErrorCode.EMPTY_TOKEN);
        }
        throw new JwtException(JwtErrorCode.INVALID_TOKEN);
    }
}
