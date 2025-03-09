package org.terning.terningserver.jwt.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtClaimsGenerator {

    private static final String USER_ID_CLAIM = "userId";

    public Claims generateClaims(Authentication authentication) {
        Claims claims = Jwts.claims();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            claims.put(USER_ID_CLAIM, userDetails.getUsername());
            return claims;
        }

        claims.put(USER_ID_CLAIM, principal.toString());
        return claims;
    }
}
