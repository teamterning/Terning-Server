package org.terning.terningserver.common.security.jwt.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.terning.terningserver.common.security.jwt.exception.JwtErrorCode;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtClaimsGenerator {

    private static final String USER_ID_CLAIM = "userId";

    public Claims generateClaims(Authentication authentication) {
        return Jwts.claims(createClaimsMap(authentication));
    }

    private Map<String, Object> createClaimsMap(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Long userId) {
            return Map.of(USER_ID_CLAIM, userId);
        }

        throw new JwtException(JwtErrorCode.INVALID_USER_DETAILS_TYPE.getMessage());
    }
}
