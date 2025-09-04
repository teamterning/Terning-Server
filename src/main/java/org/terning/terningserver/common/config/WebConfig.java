package org.terning.terningserver.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.terning.terningserver.auth.config.LoginCheckInterceptor;
import org.terning.terningserver.auth.config.LoginUserArgumentResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    private static final String[] AUTH_WHITELIST = {
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/swagger-ui/**",

        "/api/v1/auth/sign-in",
        "/api/v1/auth/sign-up",
        "/api/v1/auth/sign-up/filter",
        "/api/v1/auth/token-reissue",

        "/api/v1/search/banners",
        "/api/v1/search/views",
        "/api/v1/search/scraps",

        "/actuator/health",
        "/api/v1/external/scraps/unsynced",
        "/api/v1/external/scraps/sync/result",

        "/api/v1/users",

        "/api/v1/notification/create",
        "/api/v1/push-notifications/send-all",
        "/api/v1/external/scraps/sync"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8080",
                        "http://localhost:3000",
                        "https://www.terning-official.p-e.kr/",
                        "https://www.terning-official.n-e.kr/",
                        "http://15.165.242.132",
                        "http://54.180.215.35",
                        "http://52.78.114.66"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .order(1)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns(AUTH_WHITELIST);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }
}
