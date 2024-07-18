package org.terning.terningserver.controller.swagger;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.terning.terningserver.dto.filter.request.UserFilterRequestDto;
import org.terning.terningserver.dto.filter.response.UserFilterResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;

@Tag(name = "Filter", description = "사용자 필터링 관련 API")
public interface FilterSwagger {

    @Operation(summary = "사용자 필터링 정보 조회 API", description = "사용자가 설정한 필터링 정보를 조회하는 API")
    ResponseEntity<SuccessResponse<UserFilterResponseDto>> getUserFilter(
    );

    @Operation(summary = "사용자 필터링 정보 수정 API", description = "사용자 필터링을 수정하는 API")
    ResponseEntity<SuccessResponse> updateUserFilter(
            @RequestBody UserFilterRequestDto requestDto
    );
}
