package org.terning.terningserver.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.terning.terningserver.dto.calendar.response.DailyScrapResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyListResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;

import java.util.List;

@Tag(name = "Calendar", description = "캘린더 관련 API")
public interface CalendarSwagger {

    @Operation(summary = "캘린더 > 월간 스크랩 공고 조회", description = "월간 스크랩 공고를 조회하는 API")
    ResponseEntity<SuccessResponse<List<MonthlyDefaultResponseDto>>> getMonthlyScraps(
            String token,
            int year,
            int month
    );

    @Operation(summary = "캘린더 > 월간 스크랩 공고 조회 (리스트)", description = "월간 스크랩 공고를 리스트로 조회하는 API")
    ResponseEntity<SuccessResponse<List<MonthlyListResponseDto>>> getMonthlyScrapsAsList(
            String token,
            int year,
            int month
    );

    @Operation(summary = "캘린더 > 일간 스크랩 공고 조회 (리스트)", description = "일간 스크랩 공고를 리스트로 조회하는 API")
    ResponseEntity<SuccessResponse<List<DailyScrapResponseDto>>> getDailyScraps(
            String token,
            String date
    );
}
