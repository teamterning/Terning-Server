package org.terning.terningserver.jwt.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.jwt.parser.JwtParserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtTokenVerifier {

    private final JwtParserService jwtParserService;

    public Optional<Long> validateAndExtractUserId(String token) {
        try {
            return Optional.of(jwtParserService.extractUserId(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
