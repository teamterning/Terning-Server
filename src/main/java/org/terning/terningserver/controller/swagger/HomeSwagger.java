package org.terning.terningserver.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.terning.terningserver.dto.user.response.HomeResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;

import java.util.List;

@Tag(name = "Home", description = "홈화면 관련 API")
public interface HomeSwagger {

    @Operation(summary = "홈화면 > 나에게 딱맞는 인턴 공고 조회", description = "특정 사용자에 필터링 조건에 맞는 인턴 공고 정보를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인턴 공고를 성공적으로 조회했습니다.", content =
            @Content(mediaType = "application/json"))
    })
    ResponseEntity<SuccessResponse<List<HomeResponseDto>>> getAnnouncements(
        String token,
        String sortBy,
        int startYear,
        int startMonth
    );
}