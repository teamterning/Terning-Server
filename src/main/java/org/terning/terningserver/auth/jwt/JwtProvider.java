package org.terning.terningserver.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.terning.terningserver.auth.dto.Token;
import org.terning.terningserver.common.config.ValueConfig;
import org.terning.terningserver.auth.jwt.exception.JwtErrorCode;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String USER_ID_CLAIM = "userId";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final ValueConfig valueConfig;
    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(valueConfig.getSecretKey().getBytes());
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
        throw new JwtException(JwtErrorCode.INVALID_USER_ID_TYPE.getMessage());
    }

    public String resolveToken(String rawToken) {
        if (rawToken != null && rawToken.startsWith(TOKEN_PREFIX)) {
            return rawToken.substring(TOKEN_PREFIX.length());
        }
        throw new JwtException(JwtErrorCode.TOKEN_NOT_FOUND.getMessage());
    }

    private String generateToken(Long userId, long expiration) {
        Claims claims = Jwts.claims();
        claims.put(USER_ID_CLAIM, userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException(JwtErrorCode.EXPIRED_JWT_TOKEN.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException | IllegalArgumentException e) {
            throw new JwtException(JwtErrorCode.INVALID_JWT_TOKEN.getMessage());
        }
    }
}
