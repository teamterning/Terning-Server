package org.terning.terningserver.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.terning.terningserver.config.ValueConfig;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtSigner {
//    private final ValueConfig valueConfig;
    private final JwtKeyProvider jwtKeyProvider;

    public String sign(Claims claims, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(jwtKeyProvider.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }
}
