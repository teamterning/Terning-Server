package org.terning.terningserver.common.security.jwt.provider;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.terning.terningserver.common.config.ValueConfig;

import javax.crypto.SecretKey;

@Component
public class JwtKeyProvider {

    public static SecretKey getSigningKey(ValueConfig valueConfig) {
        return Keys.hmacShaKeyFor(valueConfig.getSecretKey().getBytes());
    }
}
