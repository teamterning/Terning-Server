package org.terning.terningserver.common.security.jwt.filter;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.terning.terningserver.common.security.jwt.application.JwtUserIdExtractor;
import org.terning.terningserver.common.security.jwt.auth.UserAuthentication;
import org.terning.terningserver.common.security.jwt.exception.JwtException;
import org.terning.terningserver.common.security.ratelimit.RateLimitingService;
import org.terning.terningserver.common.util.IpAddressUtil;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUserIdExtractor jwtUserIdExtractor;
    private final RateLimitingService rateLimitingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional<String> token = extractToken(request);

        if (token.isPresent()) {
            try {
                Long userId = jwtUserIdExtractor.extractUserId(token.get());
                authenticateUser(userId);

            } catch (JwtException e) {
                String clientIp = IpAddressUtil.getClientIp(request);
                Bucket bucket = rateLimitingService.resolveBucket(clientIp);
                ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

                if (probe.isConsumed()) {
                    log.warn("[WARN] 유효하지 않은 JWT 토큰 요청. IP: {}. 남은 시도 횟수: {}", clientIp, probe.getRemainingTokens());
                    SecurityContextHolder.clearContext();

                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰입니다.");
                    return;

                } else {
                    long waitForRefillSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000L;
                    log.error("[ERROR] 과도한 JWT 토큰 요청. IP: {}. 요청을 차단합니다. 대기 시간: {}초", clientIp, waitForRefillSeconds);
                    response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "과도한 요청입니다. 잠시 후 다시 시도해주세요.");
                    return;
                }
            }
        }

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
