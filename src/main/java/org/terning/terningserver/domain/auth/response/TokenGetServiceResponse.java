package org.terning.terningserver.domain.auth.response;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record TokenGetServiceResponse(
        @NonNull String accessToken
) {

    public static TokenGetServiceResponse of(String accessToken) {
        return TokenGetServiceResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
