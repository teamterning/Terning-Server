package org.terning.terningserver.domain.auth.request;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignUpRequest(
        @NonNull String name,
        @NonNull int profileImage
) {

        public static SignUpRequest of(String name, int profileImage){
            return SignUpRequest.builder()
                    .name(name)
                    .profileImage(profileImage)
                    .build();
        }

}
