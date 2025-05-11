package org.terning.terningserver.common.security.jwt.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.terning.terningserver.common.security.jwt.exception.JwtErrorCode;
import org.terning.terningserver.common.security.jwt.exception.JwtException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {
        throw new JwtException(JwtErrorCode.INVALID_JWT_TOKEN);
    }
}
