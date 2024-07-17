package org.terning.terningserver.dto.auth.response;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record TokenGetResponseDto(
        String accessToken
) {

    public static TokenGetResponseDto of(String accessToken) {
        return TokenGetResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
