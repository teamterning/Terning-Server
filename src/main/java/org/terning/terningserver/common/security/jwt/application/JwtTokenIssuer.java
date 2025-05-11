package org.terning.terningserver.common.security.jwt.application;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.terning.terningserver.common.security.jwt.provider.JwtSigner;

@Component
@RequiredArgsConstructor
public class JwtTokenIssuer {

    private final JwtClaimsGenerator jwtClaimsGenerator;
    private final JwtSigner jwtSigner;

    public String generateToken(Authentication authentication, long expiration) {
        Claims claims = jwtClaimsGenerator.generateClaims(authentication);
        return jwtSigner.sign(claims, expiration);
    }
}
