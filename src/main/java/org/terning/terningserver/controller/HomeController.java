package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.HomeSwagger;
import org.terning.terningserver.dto.user.response.HomeAnnouncementsResponseDto;
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
    public ResponseEntity<SuccessResponse<HomeAnnouncementsResponseDto>> getAnnouncements(
            @AuthenticationPrincipal Long userId,
            @RequestParam(value = "sortBy", required = false, defaultValue = "deadlineSoon") String sortBy,
            @RequestParam("startYear") int startYear,
            @RequestParam("startMonth") int startMonth
    ){
        HomeAnnouncementsResponseDto announcements = homeService.getAnnouncements(userId, sortBy, startYear, startMonth);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_ANNOUNCEMENTS, announcements));
    }

    @GetMapping("/home/today")
    public ResponseEntity<SuccessResponse<List<TodayScrapResponseDto>>> getTodayScraps(
            @AuthenticationPrincipal Long userId
    ){

        List<TodayScrapResponseDto> scrapList = scrapService.getTodayScrap(userId);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_TODAY_ANNOUNCEMENTS, scrapList));
    }
}
