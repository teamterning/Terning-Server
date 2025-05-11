package org.terning.terningserver.common.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@Getter
public class ValueConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.kakao-url}")
    private String kakaoUri;

    @Value("${jwt.apple-url}")
    private String appleUri;

    @Value("${jwt.access-token-expired}")
    private Long accessTokenExpired;

    @Value("${jwt.refresh-token-expired}")
    private Long refreshTokenExpired;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}