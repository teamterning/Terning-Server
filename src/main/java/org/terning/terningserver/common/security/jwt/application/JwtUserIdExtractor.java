package org.terning.terningserver.common.security.jwt.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.terning.terningserver.common.security.jwt.exception.JwtErrorCode;
import org.terning.terningserver.common.security.jwt.exception.JwtException;
import org.terning.terningserver.common.security.jwt.auth.JwtClaimsParser;

@Component
@RequiredArgsConstructor
public class JwtUserIdExtractor {
    private static final String CLAIM_USER_ID = "userId";
    private final JwtClaimsParser jwtClaimsParser;

    public Long extractUserId(String token) {
        Claims claims = jwtClaimsParser.parse(token);
        Object userIdClaim = claims.get(CLAIM_USER_ID);

        if (userIdClaim instanceof Number) {
            return ((Number) userIdClaim).longValue();
        } else if (userIdClaim instanceof String) {
            return Long.parseLong((String) userIdClaim);
        }

        throw new JwtException(JwtErrorCode.INVALID_USER_ID_TYPE);
    }
}
