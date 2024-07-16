package org.terning.terningserver.dto.auth.request;

import lombok.Builder;
import lombok.NonNull;
import org.terning.terningserver.domain.enums.AuthType;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignUpRequestDto(
        @NonNull String name,
        @NonNull int profileImage,
        @NonNull AuthType authType
) {

        public static SignUpRequestDto of(String name, int profileImage, AuthType authType){
            return SignUpRequestDto.builder()
                    .name(name)
                    .profileImage(profileImage)
                    .authType(authType)
                    .build();
        }

}
