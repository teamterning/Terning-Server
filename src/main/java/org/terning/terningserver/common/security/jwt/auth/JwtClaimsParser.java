package org.terning.terningserver.common.security.jwt.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.common.config.ValueConfig;
import org.terning.terningserver.common.security.jwt.exception.JwtErrorCode;
import org.terning.terningserver.common.security.jwt.exception.JwtException;
import org.terning.terningserver.common.security.jwt.provider.JwtKeyProvider;

@Service
@RequiredArgsConstructor
public class JwtClaimsParser {
    private final ValueConfig valueConfig;

    public Claims parse(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(JwtKeyProvider.getSigningKey(valueConfig))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JwtException(JwtErrorCode.INVALID_JWT_TOKEN);
        }
    }
}
