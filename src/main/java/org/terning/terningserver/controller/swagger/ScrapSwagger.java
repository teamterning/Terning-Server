package org.terning.terningserver.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.dto.scrap.request.UpdateScrapRequestDto;
import org.terning.terningserver.exception.dto.SuccessResponse;

@Tag(name="Scrap", description = "스크랩 관련 API")
public interface ScrapSwagger {

    @Operation(summary = "스크랩 추가", description = "사용자가 스크랩을 추가하는 API")
    ResponseEntity<SuccessResponse> createScrap(
            @AuthenticationPrincipal Long userId, @PathVariable Long internshipAnnouncementId, @RequestBody CreateScrapRequestDto request
    );

    @Operation(summary = "스크랩 취소", description = "사용자가 스크랩을 취소하는 API")
    ResponseEntity<SuccessResponse> deleteScrap(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long internshipAnnouncementId
    );

    @Operation(summary = "스크랩 수정", description = "사용자가 스크랩 색상을 수정하는 API")
    public ResponseEntity<SuccessResponse> updateScrapColor(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long scrapId,
            @RequestBody UpdateScrapRequestDto request
    );

}
