package org.terning.terningserver.auth.dto.request;

import lombok.Builder;
import lombok.NonNull;
import org.terning.terningserver.user.domain.AuthType;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignUpRequestDto(
        @NonNull String name,
        String profileImage,
        @NonNull AuthType authType,
        String fcmToken
) {

        public static SignUpRequestDto of(String name, String profileImage, AuthType authType, String fcmToken){
            return SignUpRequestDto.builder()
                    .name(name)
                    .profileImage(profileImage)
                    .authType(authType)
                    .fcmToken(fcmToken)
                    .build();
        }

}
