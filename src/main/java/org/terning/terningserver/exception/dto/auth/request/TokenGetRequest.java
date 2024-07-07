package org.terning.terningserver.exception.dto.auth.request;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record TokenGetRequest(
        @NonNull String refreshToken
) {

    public static TokenGetRequest of(String refreshToken) {
        return TokenGetRequest.builder()
                .refreshToken(refreshToken)
                .build();
    }
}
