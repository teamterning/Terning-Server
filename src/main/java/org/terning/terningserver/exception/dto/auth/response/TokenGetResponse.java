package org.terning.terningserver.exception.dto.auth.response;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record TokenGetResponse(@NonNull String accessToken
) {

    public static TokenGetResponse of(String accessToken) {
        return TokenGetResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
