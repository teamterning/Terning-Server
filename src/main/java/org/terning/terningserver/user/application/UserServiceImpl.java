package org.terning.terningserver.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.user.domain.ProfileImage;
import org.terning.terningserver.user.domain.PushNotificationStatus;
import org.terning.terningserver.user.dto.request.ProfileUpdateRequestDto;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.common.exception.enums.ErrorMessage;
import org.terning.terningserver.external.pushNotification.notification.NotificationUserClient;
import org.terning.terningserver.external.pushNotification.user.application.UserSyncEventService;
import org.terning.terningserver.user.repository.UserRepository;
import org.terning.terningserver.user.dto.response.ProfileResponseDto;

import static org.terning.terningserver.common.exception.enums.ErrorMessage.FAILED_WITHDRAW;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserSyncEventService userSyncEventService;
    private final NotificationUserClient notificationUserClient;

    @Override
    @Transactional
    public void deleteUser(User user) {
        try {
            userRepository.delete(user);
//            userSyncEventService.recordWithdraw(user.getId());
            notificationUserClient.deleteUser(user.getId());
        } catch (Exception e) {
            throw new CustomException(FAILED_WITHDRAW);
        }
    }

    @Override
    public ProfileResponseDto getProfile(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION)
        );

        return ProfileResponseDto.of(user);
    }

    @Override
    @Transactional
    public void updateProfile(Long userId, ProfileUpdateRequestDto request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION));

        try{
            // 프로필 이미지가 유효하지 않으면 IllegalArgumentException을 던짐
            ProfileImage profileImage = ProfileImage.fromValue(request.profileImage());

            if (!user.getName().equals(request.name())) {
//                userSyncEventService.recordNameChange(userId, request.name());
                notificationUserClient.updateUserName(userId, request.name());
            }

            //프로필 업데이트
            user.updateProfile(request.name(), ProfileImage.fromValue(request.profileImage()));

            userRepository.save(user);
        } catch (IllegalArgumentException e){
            // 잘못된 프로필 이미지 값이 오면 CustomException 발생
            throw new CustomException(ErrorMessage.INVALID_PROFILE_IMAGE);
        }
    }

    @Transactional
    public void updatePushStatus(Long userId, String newStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION));
        user.setPushStatus(PushNotificationStatus.from(newStatus));
        userRepository.save(user);
    }
}
