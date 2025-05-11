package org.terning.terningserver.scrap.api;

import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_CREATE_SCRAP;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_DELETE_SCRAP;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_UPDATE_SCRAP;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.scrap.dto.request.CreateScrapRequestDto;
import org.terning.terningserver.scrap.dto.request.UpdateScrapRequestDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.scrap.application.ScrapService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScrapController implements ScrapSwagger {

    private final ScrapService scrapService;

    @PostMapping("/scraps/{internshipAnnouncementId}")
    public ResponseEntity<SuccessResponse> createScrap(
            @AuthenticationPrincipal long userId,
            @PathVariable long internshipAnnouncementId,
            @RequestBody CreateScrapRequestDto request) {
        scrapService.createScrap(internshipAnnouncementId, request, userId);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_CREATE_SCRAP));
    }

    @DeleteMapping("/scraps/{internshipAnnouncementId}")
    public ResponseEntity<SuccessResponse> deleteScrap(
            @AuthenticationPrincipal long userId,
            @PathVariable long internshipAnnouncementId) {
        scrapService.deleteScrap(internshipAnnouncementId, userId);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_DELETE_SCRAP));
    }

    @PatchMapping("/scraps/{internshipAnnouncementId}")
    public ResponseEntity<SuccessResponse> updateScrapColor(
            @AuthenticationPrincipal long userId,
            @PathVariable long internshipAnnouncementId,
            @RequestBody UpdateScrapRequestDto request) {
        scrapService.updateScrapColor(internshipAnnouncementId, request, userId);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_SCRAP));
    }
}
