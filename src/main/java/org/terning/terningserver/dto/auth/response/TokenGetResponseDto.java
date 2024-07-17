package org.terning.terningserver.dto.auth.response;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record TokenGetResponseDto(
        String refreshToken
) {

    public static TokenGetResponseDto of(String refreshToken) {
        return TokenGetResponseDto.builder()
                .refreshToken(refreshToken)
                .build();
    }
}
