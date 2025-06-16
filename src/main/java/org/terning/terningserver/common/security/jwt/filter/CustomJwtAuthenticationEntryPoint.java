package org.terning.terningserver.common.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.terning.terningserver.common.exception.dto.ErrorResponse;
import org.terning.terningserver.common.security.jwt.exception.JwtErrorCode;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        JwtErrorCode errorCode = JwtErrorCode.INVALID_JWT_TOKEN;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getStatus().value(), errorCode.getMessage());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
