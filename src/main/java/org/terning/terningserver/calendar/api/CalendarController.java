package org.terning.terningserver.calendar.api;

import org.terning.terningserver.auth.config.Login;
import org.terning.terningserver.calendar.dto.response.DailyScrapResponseDto;
import org.terning.terningserver.calendar.dto.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.calendar.dto.response.MonthlyListResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.scrap.application.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_DAILY_SCRAPS;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_MONTHLY_SCRAPS;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_MONTHLY_SCRAPS_AS_LIST;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CalendarController implements CalendarSwagger {

    private final ScrapService scrapService;

    @GetMapping("/calendar/monthly-default")
    public ResponseEntity<SuccessResponse<List<MonthlyDefaultResponseDto>>> getMonthlyScraps(
            @Login Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ){
        List<MonthlyDefaultResponseDto> monthlyScraps = scrapService.getMonthlyScraps(userId, year, month);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_MONTHLY_SCRAPS, monthlyScraps));
    }

    @GetMapping("/calendar/monthly-list")
    public ResponseEntity<SuccessResponse<List<MonthlyListResponseDto>>> getMonthlyScrapsAsList(
            @Login Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ){
        List<MonthlyListResponseDto> monthlyScraps = scrapService.getMonthlyScrapsAsList(userId, year, month);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_MONTHLY_SCRAPS_AS_LIST, monthlyScraps));
    }

    @GetMapping("/calendar/daily")
    public ResponseEntity<SuccessResponse<List<DailyScrapResponseDto>>> getDailyScraps(
            @Login Long userId,
            @RequestParam("date") String date
    ){
        LocalDate localDate = LocalDate.parse(date);
        List<DailyScrapResponseDto> dailyScraps = scrapService.getDailyScraps(userId, localDate);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_DAILY_SCRAPS, dailyScraps));
    }
}
