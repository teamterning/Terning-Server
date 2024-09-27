package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.UserSwagger;
import org.terning.terningserver.dto.user.request.ProfileUpdateRequestDto;
import org.terning.terningserver.dto.user.response.ProfileResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.service.UserService;

import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_GET_PROFILE;
import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_UPDATE_PROFILE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserProfileController implements UserSwagger {

    private final UserService userService;

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
}
