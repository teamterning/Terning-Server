package org.terning.terningserver.dto.auth.response;

import lombok.Builder;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.enums.AuthType;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignInResponseDto(
        String accessToken,
        String refreshToken,
        Long userId,
        AuthType authType
) {
    public static SignInResponseDto of(Token token, Long userId, AuthType authType) {
        return SignInResponseDto.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .userId(userId)
                .authType(authType)
                .build();
    }
}
