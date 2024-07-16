package org.terning.terningserver.dto.auth.response;

import org.terning.terningserver.domain.enums.AuthType;

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
