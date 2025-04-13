package org.terning.terningserver.jwt.provider;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;
import org.terning.terningserver.config.ValueConfig;

import javax.crypto.SecretKey;

@Component
public class JwtKeyProvider {
    public static SecretKey getSigningKey(ValueConfig valueConfig) {
        return Keys.hmacShaKeyFor(valueConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }
}
