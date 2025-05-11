package org.terning.terningserver.calendar.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.terning.terningserver.calendar.dto.response.DailyScrapResponseDto;
import org.terning.terningserver.calendar.dto.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.calendar.dto.response.MonthlyListResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;

import java.util.List;

@Tag(name = "Calendar", description = "캘린더 관련 API")
public interface CalendarSwagger {

    @Operation(summary = "캘린더 > 월간 스크랩 공고 조회", description = "월간 스크랩 공고를 조회하는 API")
    ResponseEntity<SuccessResponse<List<MonthlyDefaultResponseDto>>> getMonthlyScraps(
            Long userId,
            int year,
            int month
    );

    @Operation(summary = "캘린더 > 월간 스크랩 공고 조회 (리스트)", description = "월간 스크랩 공고를 리스트로 조회하는 API")
    ResponseEntity<SuccessResponse<List<MonthlyListResponseDto>>> getMonthlyScrapsAsList(
            Long userId,
            int year,
            int month
    );

    @Operation(summary = "캘린더 > 일간 스크랩 공고 조회 (리스트)", description = "일간 스크랩 공고를 리스트로 조회하는 API")
    ResponseEntity<SuccessResponse<List<DailyScrapResponseDto>>> getDailyScraps(
            Long userId,
            String date
    );
}
