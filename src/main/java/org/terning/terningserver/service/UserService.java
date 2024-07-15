package org.terning.terningserver.service;

import org.terning.terningserver.dto.user.response.ProfileResponseDto;

public interface UserService {
    ProfileResponseDto getProfile(Long userId);
}
