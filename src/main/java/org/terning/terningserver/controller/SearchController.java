package org.terning.terningserver.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.controller.swagger.SearchSwagger;
import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.service.SearchService;

import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_GET_MOST_SCRAPPED_ANNOUNCEMENTS;
import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_GET_MOST_VIEWED_ANNOUNCEMENTS;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchController implements SearchSwagger {

    private final SearchService searchService;

    @GetMapping("/search/views")
    public ResponseEntity<SuccessResponse<PopularAnnouncementListResponseDto>> getMostViewedAnnouncements() {

        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_MOST_VIEWED_ANNOUNCEMENTS,
                searchService.getMostViewedAnnouncements()
        ));
    }

    @GetMapping("/search/scraps")
    public ResponseEntity<SuccessResponse<PopularAnnouncementListResponseDto>> getMostScrappedAnnouncements() {
        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_MOST_SCRAPPED_ANNOUNCEMENTS,
                searchService.getMostScrappedAnnouncements()
        ));
    }

}
