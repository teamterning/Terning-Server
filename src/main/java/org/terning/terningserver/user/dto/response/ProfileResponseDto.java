package org.terning.terningserver.user.dto.response;

import lombok.Builder;
import org.terning.terningserver.user.domain.User;

@Builder
public record ProfileResponseDto(
        String name,
        String profileImage,
        String authType,
        String pushStatus
) {
    public static ProfileResponseDto of(final User user){
        return ProfileResponseDto.builder()
                .name(user.getName())
                .profileImage(user.getProfileImage().getValue())
                .authType(user.getAuthType().name().toUpperCase())
                .pushStatus(user.getPushStatus().value().toUpperCase())
                .build();
    }
}
