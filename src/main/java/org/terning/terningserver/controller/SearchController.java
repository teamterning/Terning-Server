package org.terning.terningserver.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.controller.swagger.SearchSwagger;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponse;
import org.terning.terningserver.dto.search.response.SearchResultResponse;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.exception.enums.SuccessMessage;
import org.terning.terningserver.service.SearchService;
import org.terning.terningserver.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_GET_MOST_SCRAPPED_ANNOUNCEMENTS;
import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_GET_MOST_VIEWED_ANNOUNCEMENTS;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchController implements SearchSwagger {

    private final SearchService searchService;

    @GetMapping("/search/views")
    public ResponseEntity<SuccessResponse<PopularAnnouncementListResponse>> getMostViewedAnnouncements() {

        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_MOST_VIEWED_ANNOUNCEMENTS,
                searchService.getMostViewedAnnouncements()
        ));
    }

    @GetMapping("/search/scraps")
    public ResponseEntity<SuccessResponse<PopularAnnouncementListResponse>> getMostScrappedAnnouncements() {
        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_MOST_SCRAPPED_ANNOUNCEMENTS,
                searchService.getMostScrappedAnnouncements()
        ));
    }

    @GetMapping("/search")
    public SearchResultResponse searchInternshipAnnouncement(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam("sortBy") String sortBy, Pageable pageable) {
        return searchService.searchInternshipAnnouncement(keyword, sortBy, pageable);
    }

}
