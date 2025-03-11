package org.terning.terningserver.jwt.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtClaimsGenerator {

    private static final String USER_ID_CLAIM = "userId";

    public Claims generateClaims(Authentication authentication) {
        return Jwts.claims(createClaimsMap(authentication));
    }

    private Map<String, Object> createClaimsMap(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return Map.of(USER_ID_CLAIM, userDetails.getUsername());
        }

        return Map.of(USER_ID_CLAIM, principal.toString());
    }
}

