package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.HomeSwagger;
import org.terning.terningserver.dto.user.response.HomeAnnouncementsResponseDto;
import org.terning.terningserver.dto.user.response.UpcomingScrapResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.service.HomeService;
import org.terning.terningserver.service.ScrapService;

import java.util.List;

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
            @PageableDefault(size = 10) Pageable pageable) {
        HomeAnnouncementsResponseDto announcements = homeService.getAnnouncements(userId, sortBy, pageable);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_ANNOUNCEMENTS, announcements));
    }


    @GetMapping("/home/upcoming")
    public ResponseEntity<SuccessResponse<List<UpcomingScrapResponseDto>>> getUpcomingScraps(
            @AuthenticationPrincipal Long userId
    ){

        boolean hasScrapped = scrapService.hasUserScrapped(userId);
        List<UpcomingScrapResponseDto.ScrapDetail> scrapList = scrapService.getUpcomingScrap(userId);

        UpcomingScrapResponseDto responseDto = new UpcomingScrapResponseDto(hasScrapped, scrapList);

        if(!hasScrapped){
            return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_UPCOMING_ANNOUNCEMENTS_NO_SCRAP, responseDto));
        } else if (scrapList.isEmpty()) {
            return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_UPCOMING_ANNOUNCEMENTS_EMPTY_LIST, responseDto));
        } else {
            return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_UPCOMING_ANNOUNCEMENTS, responseDto));
        }
    }
}
