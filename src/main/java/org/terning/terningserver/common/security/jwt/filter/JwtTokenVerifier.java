package org.terning.terningserver.common.security.jwt.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.common.security.jwt.application.JwtUserIdExtractor;
import org.terning.terningserver.common.security.jwt.exception.JwtException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtTokenVerifier {

    private final JwtUserIdExtractor jwtUserIdExtractor;

    public Optional<Long> validateAndExtractUserId(String token) throws JwtException {
        return Optional.of(jwtUserIdExtractor.extractUserId(token));
    }
}
