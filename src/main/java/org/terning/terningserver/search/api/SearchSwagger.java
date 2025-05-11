package org.terning.terningserver.search.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.terning.terningserver.search.dto.response.PopularAnnouncementListResponseDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.terning.terningserver.search.dto.response.SearchResultResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;

@Tag(name = "Search", description = "탐색 관련 API")
public interface SearchSwagger {

    @Operation(summary = "탐색 > 지금 조회수 많은 공고", description = "탐색 화면에서 조회수 많은 공고를 불러오는 API")
    ResponseEntity<SuccessResponse<PopularAnnouncementListResponseDto>> getMostViewedAnnouncements(

    );

    @Operation(summary = "탐색 > 지금 스크랩 수 많은 공고", description = "탐색 화면에서 스크랩 수 많은 공고를 불러오는 API")
    ResponseEntity<SuccessResponse<PopularAnnouncementListResponseDto>> getMostScrappedAnnouncements(

    );

    @Operation(summary = "탐색 > 검색 결과 화면", description = "탐색 화면에서 인턴 공고를 검색하는 API")
    ResponseEntity<SuccessResponse<SearchResultResponseDto>> searchInternshipAnnouncement(
            @AuthenticationPrincipal Long userId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam("sortBy") String sortBy, Pageable pageable
    );
}
