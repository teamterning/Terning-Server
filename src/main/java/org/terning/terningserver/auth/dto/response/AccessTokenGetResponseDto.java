package org.terning.terningserver.auth.dto.response;

import lombok.Builder;
import lombok.NonNull;
import org.terning.terningserver.domain.Token;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record AccessTokenGetResponseDto(
        @NonNull String accessToken
) {

    public static AccessTokenGetResponseDto of(Token accessToken) {
        return AccessTokenGetResponseDto.builder()
                .accessToken(accessToken.getAccessToken())
                .build();
    }
}
