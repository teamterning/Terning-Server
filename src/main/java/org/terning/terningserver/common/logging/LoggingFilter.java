package org.terning.terningserver.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";
    private static final String X_FORWARDED_FOR_HEADER = "X-FORWARDED-FOR";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request = new CachedHttpServletRequest(request);

        MDC.put(TRACE_ID, UUID.randomUUID().toString());

        String requestLine = request.getMethod() + " " + request.getRequestURI() + getRequestParams(request);
        log.info("Request ({}): {} {}", getRequestAddr(request), requestLine, request.getReader().lines().collect(Collectors.joining()));

        filterChain.doFilter(request, response);

        log.info("Response: {} : {}", requestLine, response.getStatus());
    }

    private String getRequestAddr(HttpServletRequest request) {
        String requestAddr = request.getHeader(X_FORWARDED_FOR_HEADER);
        return requestAddr != null ? requestAddr : request.getRemoteAddr();
    }

    private String getRequestParams(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();

        if (paramMap.isEmpty()) {
            return "";
        }

        String queryParameters = paramMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + (entry.getValue().length > 0 ? entry.getValue()[0] : ""))
                .collect(Collectors.joining("&"));

        return "?" + queryParameters;
    }
}
