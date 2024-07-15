package org.terning.terningserver.controller.swagger;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;

@Tag(name = "Search", description = "탐색 관련 API")
public interface SearchSwagger {

    @Operation(summary = "탐색 > 지금 조회수 많은 공고", description = "탐색 화면에서 조회수 많은 공고를 불러오는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<SuccessResponse<PopularAnnouncementListResponseDto>> getMostViewedAnnouncements(

    );

    @Operation(summary = "탐색 > 지금 스크랩 수 많은 공고", description = "탐색 화면에서 스크랩 수 많은 공고를 불러오는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<SuccessResponse<PopularAnnouncementListResponseDto>> getMostScrappedAnnouncements(

    );
}
