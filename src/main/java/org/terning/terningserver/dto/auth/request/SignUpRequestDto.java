package org.terning.terningserver.dto.auth.request;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignUpRequestDto(
        @NonNull String name,
        @NonNull int profileImage
) {

        public static SignUpRequestDto of(String name, int profileImage){
            return SignUpRequestDto.builder()
                    .name(name)
                    .profileImage(profileImage)
                    .build();
        }

}
