package org.terning.terningserver.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.terning.terningserver.common.security.jwt.application.JwtUserIdExtractor;
import org.terning.terningserver.common.security.jwt.filter.CustomJwtAuthenticationEntryPoint;
import org.terning.terningserver.common.security.jwt.filter.JwtAuthenticationFilter;
import org.terning.terningserver.common.security.ratelimit.RateLimitingService;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUserIdExtractor jwtUserIdExtractor;
    private final RateLimitingService rateLimitingService;
    private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;

    private static final List<String> AUTH_WHITELIST = List.of(
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/api/v1/swagger-ui/index.html#/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/api/v1/auth/**",
            "/actuator/health",
            "/api/v1/users/**",
            "/api/v1/push-status",
            "/api/v1/external/scraps/unsynced",
            "/api/v1/external/scraps/sync/result"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
                jwtUserIdExtractor,
                rateLimitingService,
                AUTH_WHITELIST
        );

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(customJwtAuthenticationEntryPoint))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AUTH_WHITELIST.toArray(new String[0])).permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
