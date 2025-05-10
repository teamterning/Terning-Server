package org.terning.terningserver.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.user.dto.request.ProfileUpdateRequestDto;
import org.terning.terningserver.user.dto.request.PushStatusUpdateRequest;
import org.terning.terningserver.user.dto.response.ProfileResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.common.exception.enums.SuccessMessage;
import org.terning.terningserver.external.pushNotification.notification.NotificationUserClient;
import org.terning.terningserver.user.application.UserService;

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
            @AuthenticationPrincipal Long userId
    ){
        ProfileResponseDto profile = userService.getProfile(userId);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_PROFILE, profile));
    }

    @PatchMapping("/mypage/profile")
    public ResponseEntity<SuccessResponse> updateProfile(
            @AuthenticationPrincipal Long userId,
            @RequestBody ProfileUpdateRequestDto request
    ){
        userService.updateProfile(userId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_PROFILE));
    }

    @PatchMapping("/push-status")
    public ResponseEntity<SuccessResponse<Void>> updatePushStatus(
            @AuthenticationPrincipal Long userId,
            @RequestBody PushStatusUpdateRequest request
    ) {
        // 운영서버에서 User 엔티티 업데이트
        userService.updatePushStatus(userId, request.newStatus());

        // 알림서버에 변경사항 동기화
        notificationUserClient.updatePushStatus(userId, request.newStatus());

        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.PUSH_STATUS_UPDATED));
    }
}
