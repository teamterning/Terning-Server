package org.terning.terningserver.domain.auth.response;

import lombok.Builder;
import lombok.NonNull;
import org.terning.terningserver.domain.Token;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignInServiceResponse(
        @NonNull String accessToken,
        @NonNull String refreshToken
) {

    public static SignInServiceResponse of(Token token) {
        return SignInServiceResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
