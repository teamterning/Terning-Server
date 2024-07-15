package org.terning.terningserver.dto.auth.request;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record TokenGetRequestDto(
        @NonNull String refreshToken
) {

    public static TokenGetRequestDto of(String refreshToken) {
        return TokenGetRequestDto.builder()
                .refreshToken(refreshToken)
                .build();
    }
}
