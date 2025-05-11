package org.terning.terningserver.user.dto.request;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record ProfileUpdateRequestDto(
        @NonNull String name,
        String profileImage
) {
    public static ProfileUpdateRequestDto of(String name, String profileImage){
        return ProfileUpdateRequestDto.builder()
                .name(name)
                .profileImage(profileImage)
                .build();
    }
}
