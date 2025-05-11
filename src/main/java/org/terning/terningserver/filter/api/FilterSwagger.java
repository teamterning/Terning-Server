package org.terning.terningserver.filter.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.terning.terningserver.filter.dto.request.UpdateUserFilterRequestDto;
import org.terning.terningserver.filter.dto.response.UserFilterDetailResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;

@Tag(name = "Filter", description = "사용자 필터링 관련 API")
public interface FilterSwagger {

    @Operation(summary = "사용자 필터링 정보 조회 API", description = "사용자가 설정한 필터링 정보를 조회하는 API")
    ResponseEntity<SuccessResponse<UserFilterDetailResponseDto>> getUserFilter(
            @AuthenticationPrincipal long userId
    );

    @Operation(summary = "사용자 필터링 정보 수정 API", description = "사용자 필터링을 수정하는 API")
    ResponseEntity<SuccessResponse> updateUserFilter(
            @AuthenticationPrincipal long userId,
            @RequestBody UpdateUserFilterRequestDto requestDto
    );
}
