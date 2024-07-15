package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.CalendarSwagger;
import org.terning.terningserver.dto.calendar.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyListResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.service.ScrapService;

import java.util.List;

import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_GET_MONTHLY_SCRAPS;
import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_GET_MONTHLY_SCRAPS_AS_LIST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CalendarController implements CalendarSwagger {

    private final ScrapService scrapService;

    @GetMapping("/calendar/monthly-default")
    public ResponseEntity<SuccessResponse<List<MonthlyDefaultResponseDto>>> getMonthlyScraps(
            @RequestHeader("Authorization") String token,
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ){
        Long userId = getUserIdFromToken(token);
        List<MonthlyDefaultResponseDto> monthlyScraps = scrapService.getMonthlyScraps(userId, year, month);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_MONTHLY_SCRAPS, monthlyScraps));
    }

    @GetMapping("/calendar/monthly-list")
    public ResponseEntity<SuccessResponse<List<MonthlyListResponseDto>>> getMonthlyScrapsAsList(
            @RequestHeader("Authorization") String token,
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ){
        Long userId = getUserIdFromToken(token);
        List<MonthlyListResponseDto> monthlyScraps = scrapService.getMonthlyScrapsAsList(userId, year, month);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_MONTHLY_SCRAPS_AS_LIST, monthlyScraps));
    }

    private Long getUserIdFromToken(String token){
        //실제 토큰에서 userId를 받아오는 로직 구현
        return 1L;  //임시로 사용자 ID 1로 반환
    }
}
