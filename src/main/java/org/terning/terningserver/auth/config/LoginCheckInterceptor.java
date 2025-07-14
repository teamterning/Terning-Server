package org.terning.terningserver.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.terning.terningserver.auth.jwt.JwtProvider;
import org.terning.terningserver.auth.jwt.exception.JwtException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USER_ID_ATTRIBUTE_NAME = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        try {
            Optional<Long> userIdOpt = Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                    .map(jwtProvider::getUserIdFrom);

            if (userIdOpt.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            request.setAttribute(USER_ID_ATTRIBUTE_NAME, userIdOpt.get());
            return true;

        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
