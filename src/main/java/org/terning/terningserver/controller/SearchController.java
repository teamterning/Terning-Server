package org.terning.terningserver.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponse;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.exception.enums.SuccessMessage;
import org.terning.terningserver.service.SearchService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search/views")
    public ResponseEntity<SuccessResponse<PopularAnnouncementListResponse>> getPopularAnnouncements() {
        return ResponseEntity.ok(SuccessResponse.of(
                SuccessMessage.SUCCESS_GET_MOST_VIEWED_ANNOUNCEMENTS,
                searchService.getMostViewedAnnouncements())
        );
    }

}
