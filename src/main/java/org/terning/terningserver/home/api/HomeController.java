package org.terning.terningserver.home.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.auth.config.Login;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.home.application.HomeService;
import org.terning.terningserver.home.dto.response.HomeAnnouncementsResponseDto;
import org.terning.terningserver.home.dto.response.UpcomingScrapResponseDto;
import org.terning.terningserver.scrap.application.ScrapService;

import java.util.List;

import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_ANNOUNCEMENTS;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_UPCOMING_ANNOUNCEMENTS;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_UPCOMING_ANNOUNCEMENTS_EMPTY_LIST;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_UPCOMING_ANNOUNCEMENTS_NO_SCRAP;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController implements HomeSwagger {

    private final HomeService homeService;
    private final ScrapService scrapService;

    @GetMapping("/home")
    public ResponseEntity<SuccessResponse<HomeAnnouncementsResponseDto>> getAnnouncements(
            @Login Long userId,
            @RequestParam(value = "sortBy", required = false, defaultValue = "deadlineSoon") String sortBy,
            @PageableDefault(size = 10) Pageable pageable) {
        HomeAnnouncementsResponseDto announcements = homeService.getAnnouncements(userId, sortBy, pageable);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_GET_ANNOUNCEMENTS, announcements));
    }


    @GetMapping("/home/upcoming")
    public ResponseEntity<SuccessResponse<List<UpcomingScrapResponseDto>>> getUpcomingScraps(
            @Login Long userId
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
