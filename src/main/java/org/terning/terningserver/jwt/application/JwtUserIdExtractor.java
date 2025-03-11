package org.terning.terningserver.jwt.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.terning.terningserver.jwt.auth.JwtClaimsParser;
import org.terning.terningserver.jwt.auth.UserIdConverter;
import org.terning.terningserver.jwt.exception.JwtErrorCode;
import org.terning.terningserver.jwt.exception.JwtException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtUserIdExtractor {
    private static final String CLAIM_USER_ID = "userId";
    private final JwtClaimsParser jwtClaimsParser;

    public Long extractUserId(String token) {
        Claims claims = jwtClaimsParser.parse(token);
        return Optional.ofNullable(claims.get(CLAIM_USER_ID))
                .map(UserIdConverter::convertToLong)
                .orElseThrow(() -> new JwtException(JwtErrorCode.INVALID_USER_ID_TYPE));
    }
}
