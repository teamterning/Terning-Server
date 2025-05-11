package org.terning.terningserver.auth.application.syncUser;

import static org.terning.terningserver.common.exception.enums.ErrorMessage.NOT_FOUND_USER_EXCEPTION;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.auth.dto.request.FcmTokenSyncRequest;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.external.pushNotification.notification.NotificationUserClient;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthSyncUserServiceImpl implements AuthSyncUserService {

    private final UserRepository userRepository;
    private final NotificationUserClient notificationUserClient;

    @Transactional
    @Override
    public void syncUser(long userId, FcmTokenSyncRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_EXCEPTION));

        notificationUserClient.createOrUpdateUser(user, request.fcmToken());
    }
}
