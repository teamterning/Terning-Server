package org.terning.terningserver.auth.dto.request;

import lombok.Builder;
import lombok.NonNull;
import org.terning.terningserver.domain.enums.AuthType;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignUpRequestDto(
        @NonNull String name,
        String profileImage,
        @NonNull AuthType authType
) {

        public static SignUpRequestDto of(String name, String profileImage, AuthType authType){
            return SignUpRequestDto.builder()
                    .name(name)
                    .profileImage(profileImage)
                    .authType(authType)
                    .build();
        }

}
