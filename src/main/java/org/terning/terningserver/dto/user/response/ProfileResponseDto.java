package org.terning.terningserver.dto.user.response;

import lombok.Builder;
import org.terning.terningserver.domain.User;

@Builder
public record ProfileResponseDto(
        String name,
        String authType
) {
    public static ProfileResponseDto of(final User user){
        return ProfileResponseDto.builder()
                .name(user.getName())
                .authType(user.getAuthType().name().toUpperCase())
                .build();
    }
}
