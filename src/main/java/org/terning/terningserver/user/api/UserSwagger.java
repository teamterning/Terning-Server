package org.terning.terningserver.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.terning.terningserver.user.dto.request.ProfileUpdateRequestDto;
import org.terning.terningserver.user.dto.request.PushStatusUpdateRequest;
import org.terning.terningserver.user.dto.response.ProfileResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;

@Tag(name = "Mypage", description = "마이페이지 관련 API")
public interface UserSwagger {

    @Operation(summary = "마이페이지 > 프로필 정보 불러오기", description = "마이페이지에서 프로필 정보를 불러오는 API")
    ResponseEntity<SuccessResponse<ProfileResponseDto>> getProfile(
            Long userId
    );

    @Operation(summary = "마이페이지 > 프로필 정보 수정하기", description = "마이페이지에서 프로필 정보를 수정하는 API")
    ResponseEntity<SuccessResponse> updateProfile(
            Long userId,
            ProfileUpdateRequestDto request
    );

    @Operation(summary = "마이페이지 > 푸시알림 상태 변경하기", description = "마이페이지에서 푸시알림 허용 여부를 수정하는 API")
    ResponseEntity<SuccessResponse<Void>> updatePushStatus(
            Long userId,
            PushStatusUpdateRequest request
    );

}
