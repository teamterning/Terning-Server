package org.terning.terningserver.service;

import org.terning.terningserver.domain.User;
import org.terning.terningserver.dto.user.response.ProfileResponseDto;

public interface UserService {
    void deleteUser(User user);
    ProfileResponseDto getProfile(Long userId);
}
