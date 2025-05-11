package org.terning.terningserver.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8080",
                        "http://localhost:3000",
                        "https://www.terning-official.p-e.kr/",
                        "https://www.terning-official.n-e.kr/",
                        "http://15.165.242.132",
                        "http://54.180.215.35") // 허용할 출처 : 특정 도메인만 받을 수 있음
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // 허용할 HTTP method
                .allowCredentials(true); // 쿠키 인증 요청 허용
    }
}
