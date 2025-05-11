package org.terning.terningserver.common.security.jwt.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.common.security.jwt.application.JwtUserIdExtractor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtTokenVerifier {

    private final JwtUserIdExtractor jwtUserIdExtractor;

    public Optional<Long> validateAndExtractUserId(String token) {
        try {
            return Optional.of(jwtUserIdExtractor.extractUserId(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
