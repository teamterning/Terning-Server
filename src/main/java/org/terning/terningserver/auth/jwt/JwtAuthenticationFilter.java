package org.terning.terningserver.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.terning.terningserver.config.ValueConfig;

import java.io.IOException;

import static io.jsonwebtoken.lang.Strings.hasText;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            val token = getAccessTokenFromRequest(request);
            if (hasText(token) && jwtTokenProvider.validateToken(token) == JwtValidationType.VALID_JWT) {
                val authentication = new UserAuthentication(getUserId(token), null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private long getUserId(String token) {
        return jwtTokenProvider.getUserFromJwt(token);
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        return isContainsAccessToken(request) ? getAuthorizationAccessToken(request) : null;
    }

    private boolean isContainsAccessToken(HttpServletRequest request) {
        val authorization = request.getHeader(AUTHORIZATION);
        return authorization != null && authorization.startsWith(ValueConfig.BEARER_HEADER);
    }

    private String getAuthorizationAccessToken(HttpServletRequest request) {
        return getTokenFromBearerString(request.getHeader(AUTHORIZATION));
    }

    private String getTokenFromBearerString(String token) {
        return token.replaceFirst(ValueConfig.BEARER_HEADER, ValueConfig.BLANK);
    }

}
