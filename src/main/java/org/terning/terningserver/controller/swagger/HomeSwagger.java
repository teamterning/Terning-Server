package org.terning.terningserver.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.terning.terningserver.dto.user.response.HomeAnnouncementsResponseDto;
import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;

import java.util.List;

@Tag(name = "Home", description = "홈화면 관련 API")
public interface HomeSwagger {

    @Operation(summary = "홈화면 > 나에게 딱맞는 인턴 공고 조회", description = "특정 사용자에 필터링 조건에 맞는 인턴 공고 정보를 조회하는 API")
    ResponseEntity<SuccessResponse<HomeAnnouncementsResponseDto>> getAnnouncements(
            Long userId,
            String sortBy,
            int startYear,
            int startMonth
    );

    @Operation(summary = "홈화면 > 오늘 마감인 스크랩 공고 조회", description = "오늘 마감인 스크랩 공고를 조회하는 API")
    ResponseEntity<SuccessResponse<List<TodayScrapResponseDto>>> getTodayScraps(
            Long userId
    );
}
