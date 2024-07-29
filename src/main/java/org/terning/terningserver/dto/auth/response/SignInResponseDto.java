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
        String authId,
        AuthType authType
) {
    public static SignInResponseDto of(Token token, String authId, AuthType authType, Long userId) {
        return SignInResponseDto.builder()
                .accessToken(token == null ? null : token.getAccessToken())
                .refreshToken(token == null ? null : token.getRefreshToken())
                .authId(authId)
                .userId(userId)
                .authType(authType)
                .build();
    }
}
