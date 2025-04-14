package org.terning.terningserver.jwt.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.jwt.exception.JwtErrorCode;
import org.terning.terningserver.jwt.exception.JwtException;
import org.terning.terningserver.jwt.provider.JwtKeyProvider;

@Service
@RequiredArgsConstructor
public class JwtClaimsParser {
//    private final ValueConfig valueConfig;
    private final JwtKeyProvider jwtKeyProvider;

    public Claims parse(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtKeyProvider.getPublicKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JwtException(JwtErrorCode.INVALID_JWT_TOKEN);
        }
    }
}
