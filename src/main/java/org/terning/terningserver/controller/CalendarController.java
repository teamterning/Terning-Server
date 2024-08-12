package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.CalendarSwagger;
import org.terning.terningserver.dto.calendar.response.DailyScrapResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyListResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.service.ScrapService;

import java.time.LocalDate;
import java.util.List;

import static org.terning.terningserver.exception.enums.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CalendarController implements CalendarSwagger {

    private final ScrapService scrapService;

    @GetMapping("/calendar/monthly-default")
    public ResponseEntity<SuccessResponse<List<MonthlyDefaultResponseDto>>> getMonthlyScraps(
            @AuthenticationPrincipal Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ){
        List<MonthlyDefaultResponseDto> monthlyScraps = scrapService.getMonthlyScraps(userId, year, month);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_MONTHLY_SCRAPS, monthlyScraps));
    }

    @GetMapping("/calendar/monthly-list")
    public ResponseEntity<SuccessResponse<List<MonthlyListResponseDto>>> getMonthlyScrapsAsList(
            @AuthenticationPrincipal Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ){
        List<MonthlyListResponseDto> monthlyScraps = scrapService.getMonthlyScrapsAsList(userId, year, month);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_MONTHLY_SCRAPS_AS_LIST, monthlyScraps));
    }

    @GetMapping("/calendar/daily")
    public ResponseEntity<SuccessResponse<List<DailyScrapResponseDto>>> getDailyScraps(
            @AuthenticationPrincipal Long userId,
            @RequestParam("date") String date
    ){
        LocalDate localDate = LocalDate.parse(date);
        List<DailyScrapResponseDto> dailyScraps = scrapService.getDailyScraps(userId, localDate);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_DAILY_SCRAPS, dailyScraps));
    }
}
