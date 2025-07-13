package org.terning.terningserver.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
}
