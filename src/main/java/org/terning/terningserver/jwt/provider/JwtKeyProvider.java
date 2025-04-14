package org.terning.terningserver.jwt.provider;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.springframework.stereotype.Component;
import org.terning.terningserver.config.ValueConfig;

import javax.crypto.SecretKey;

@Component
public class JwtKeyProvider {
    private final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public static SecretKey getSigningKey(ValueConfig valueConfig) {
        return Keys.hmacShaKeyFor(valueConfig.getSecretKey().getBytes());
    }
}
