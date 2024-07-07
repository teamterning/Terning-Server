package org.terning.terningserver.domain.auth.request;

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
