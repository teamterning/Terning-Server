package org.terning.terningserver.common.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.terning.terningserver.common.security.jwt.auth.UserAuthentication;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenVerifier jwtTokenVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractToken(request)
                .flatMap(jwtTokenVerifier::validateAndExtractUserId)
                .ifPresent(this::authenticateUser);

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .map(token -> token.replaceFirst("Bearer ", "").trim());
    }

    private void authenticateUser(Long userId) {
        UserAuthentication authentication = new UserAuthentication(userId);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
