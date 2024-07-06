package org.terning.terningserver.exception.dto.auth.response;

import lombok.Builder;
import org.terning.terningserver.domain.Token;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignInResponse(
        String accessToken,
        String refreshToken
) {
    public static SignInResponse of(Token token) {
        return SignInResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
