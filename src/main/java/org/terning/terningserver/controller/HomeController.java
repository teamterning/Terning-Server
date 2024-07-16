package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.HomeSwagger;
import org.terning.terningserver.dto.user.response.HomeResponseDto;
import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.exception.dto.ErrorResponse;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.exception.enums.SuccessMessage;
import org.terning.terningserver.service.HomeService;
import org.terning.terningserver.service.ScrapService;

import java.util.List;

import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_GET_ANNOUNCEMENTS;
import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_GET_TODAY_ANNOUNCEMENTS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController implements HomeSwagger {

    private final HomeService homeService;
    private final ScrapService scrapService;

    @GetMapping("/home")
    public ResponseEntity<SuccessResponse<List<HomeResponseDto>>> getAnnouncements(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "sortBy", required = false, defaultValue = "deadlineSoon") String sortBy,
            @RequestParam("startYear") int startYear,
            @RequestParam("startMonth") int startMonth
    ){
        List<HomeResponseDto> announcements = homeService.getAnnouncements(token, sortBy, startYear, startMonth);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_ANNOUNCEMENTS, announcements));
    }

    @GetMapping("/home/today")
    public ResponseEntity<SuccessResponse<List<TodayScrapResponseDto>>> getTodayScraps(
            @RequestHeader("Authorization") String token
    ){
        Long userId = getUserIdFromToken(token);

        List<TodayScrapResponseDto> scrapList = scrapService.getTodayScrap(userId);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_TODAY_ANNOUNCEMENTS, scrapList));
    }

    private Long getUserIdFromToken(String token){
        //실제 토큰에서 userId를 가져오는 로직 구현
        return 1L; //임시로 사용자 ID 1로 반환
    }
}
