package org.terning.terningserver.user.application;

import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.user.dto.request.ProfileUpdateRequestDto;
import org.terning.terningserver.user.dto.response.ProfileResponseDto;

public interface UserService {
    void deleteUser(User user);
    ProfileResponseDto getProfile(Long userId);

    void updateProfile(Long userId, ProfileUpdateRequestDto request);
    void updatePushStatus(Long userId, String newStatus);
}
