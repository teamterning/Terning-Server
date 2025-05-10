package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.ScrapSwagger;
import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.dto.scrap.request.UpdateScrapRequestDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.service.ScrapService;

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
