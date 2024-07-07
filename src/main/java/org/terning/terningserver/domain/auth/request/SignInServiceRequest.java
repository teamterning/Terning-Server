package org.terning.terningserver.domain.auth.request;

import lombok.Builder;
import lombok.NonNull;
import org.terning.terningserver.domain.enums.AuthType;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record SignInServiceRequest(
        @NonNull String authAccessToken,
        @NonNull AuthType authType
) {

        public static SignInServiceRequest of(String authAccessToken, SignInRequest request) {
            return SignInServiceRequest.builder()
                    .authAccessToken(authAccessToken)
                    .authType(request.authType())
                    .build();
        }
}
