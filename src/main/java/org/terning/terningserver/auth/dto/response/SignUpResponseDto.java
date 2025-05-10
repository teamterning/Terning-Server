package org.terning.terningserver.auth.dto.response;

import org.terning.terningserver.user.domain.AuthType;

public record SignUpResponseDto(
        String accessToken,
        String refreshToken,
        Long userId,
        AuthType authType
) {
    public static SignUpResponseDto of(String accessToken, String refreshToken, Long userId, AuthType authType) {
        return new SignUpResponseDto(accessToken, refreshToken, userId, authType);
    }
}


