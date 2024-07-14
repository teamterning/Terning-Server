package org.terning.terningserver.controller.swagger;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponse;
import org.terning.terningserver.dto.search.response.SearchResultResponse;
import org.terning.terningserver.exception.dto.SuccessResponse;

@Tag(name = "Search", description = "탐색 관련 API")
public interface SearchSwagger {

    @Operation(summary = "탐색 > 지금 조회수 많은 공고", description = "탐색 화면에서 조회수 많은 공고를 불러오는 API")
    ResponseEntity<SuccessResponse<PopularAnnouncementListResponse>> getMostViewedAnnouncements(

    );

    @Operation(summary = "탐색 > 지금 스크랩 수 많은 공고", description = "탐색 화면에서 스크랩 수 많은 공고를 불러오는 API")
    ResponseEntity<SuccessResponse<PopularAnnouncementListResponse>> getMostScrappedAnnouncements(

    );

    @Operation(summary = "탐색 > 검색 결과 화면", description = "탐색 화면에서 인턴 공고를 검색하는 API")
    ResponseEntity<SuccessResponse<SearchResultResponse>> searchInternshipAnnouncement(

    );
}
