package org.terning.terningserver.dto.auth.response;

import lombok.Builder;
import lombok.NonNull;
import org.terning.terningserver.domain.Token;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record TokenGetResponseDto(
        String accessToken,
        String refreshToken

) {

    public static TokenGetResponseDto of(Token token) {
        return TokenGetResponseDto.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
