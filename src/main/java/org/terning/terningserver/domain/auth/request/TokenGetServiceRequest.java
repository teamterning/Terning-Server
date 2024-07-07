package org.terning.terningserver.domain.auth.request;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record TokenGetServiceRequest(
        @NonNull String refreshToken
) {

        public static TokenGetServiceRequest of(String refreshToken) {
            return TokenGetServiceRequest.builder()
                    .refreshToken(refreshToken)
                    .build();
        }
}
