package org.terning.terningserver.jwt.provider;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import org.springframework.stereotype.Component;
import org.terning.terningserver.config.ValueConfig;

import javax.crypto.SecretKey;

@Component
public class JwtKeyProvider {
    public static SecretKey getSigningKey(ValueConfig valueConfig) {
//        return Keys.hmacShaKeyFor(valueConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = Base64.getDecoder().decode(valueConfig.getSecretKey());
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println("Using secret key with bit length: " + (key.getEncoded().length * 8) + " bits");
        return key;
    }
}
