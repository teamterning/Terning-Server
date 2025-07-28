package org.terning.terningserver.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.auth.config.Login;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.common.exception.enums.SuccessMessage;
import org.terning.terningserver.external.pushNotification.notification.NotificationUserClient;
import org.terning.terningserver.user.application.UserService;
import org.terning.terningserver.user.dto.request.ProfileUpdateRequestDto;
import org.terning.terningserver.user.dto.request.PushStatusUpdateRequest;
import org.terning.terningserver.user.dto.response.ProfileResponseDto;

import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_PROFILE;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_UPDATE_PROFILE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserProfileController implements UserSwagger {

    private final UserService userService;
    private final NotificationUserClient notificationUserClient;

    @GetMapping("/mypage/profile")
    public ResponseEntity<SuccessResponse<ProfileResponseDto>> getProfile(
            @Login Long userId
    ){
        ProfileResponseDto profile = userService.getProfile(userId);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_PROFILE, profile));
    }

    @PatchMapping("/mypage/profile")
    public ResponseEntity<SuccessResponse> updateProfile(
            @Login Long userId,
            @RequestBody ProfileUpdateRequestDto request
    ){
        userService.updateProfile(userId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_PROFILE));
    }

    @PatchMapping("/push-status")
    public ResponseEntity<SuccessResponse<Void>> updatePushStatus(
            @Login Long userId,
            @RequestBody PushStatusUpdateRequest request
    ) {
        userService.updatePushStatus(userId, request.newStatus());

        notificationUserClient.updatePushStatus(userId, request.newStatus());

        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.PUSH_STATUS_UPDATED));
    }
}
