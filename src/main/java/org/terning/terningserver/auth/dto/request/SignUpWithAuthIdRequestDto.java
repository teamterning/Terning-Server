package org.terning.terningserver.auth.dto.request;

import lombok.Builder;
import lombok.NonNull;
import org.terning.terningserver.user.domain.AuthType;

import static lombok.AccessLevel.*;

@Builder(access = PRIVATE)
public record SignUpWithAuthIdRequestDto(
        @NonNull String authId,
        @NonNull String name,
        String profileImage,
        @NonNull AuthType authType
) {
    public static SignUpWithAuthIdRequestDto of(String authId, String name, String profileImage, AuthType authType){
        return SignUpWithAuthIdRequestDto.builder()
                .authId(authId)
                .name(name)
                .profileImage(profileImage)
                .authType(authType)
                .build();
    }
}
