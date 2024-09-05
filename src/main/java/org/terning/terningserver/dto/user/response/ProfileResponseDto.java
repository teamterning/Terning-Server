package org.terning.terningserver.dto.user.response;

import lombok.Builder;
import org.terning.terningserver.domain.User;

@Builder
public record ProfileResponseDto(
        String name,
        String profileImage,
        String authType
) {
    public static ProfileResponseDto of(final User user){
        return ProfileResponseDto.builder()
                .name(user.getName())
                .profileImage(user.getProfileImage().getValue()) // Enum to String
                .authType(user.getAuthType().name().toUpperCase())
                .build();
    }
}
