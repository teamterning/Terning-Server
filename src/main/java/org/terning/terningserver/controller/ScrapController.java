package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.ScrapSwagger;
import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.dto.scrap.request.UpdateScrapRequestDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.jwt.PrincipalHandler;
import org.terning.terningserver.service.ScrapService;

import static org.terning.terningserver.exception.enums.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScrapController implements ScrapSwagger {

    private final ScrapService scrapService;
    private final PrincipalHandler principalHandler;

    @PostMapping("/scraps/{internshipAnnouncementId}")
    public ResponseEntity<SuccessResponse> createScrap(
            @PathVariable Long internshipAnnouncementId,
            @RequestBody CreateScrapRequestDto request) {
        scrapService.createScrap(internshipAnnouncementId, request, principalHandler.getUserFromPrincipal());
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_CREATE_SCRAP));
    }

    @DeleteMapping("/scraps/{scrapId}")
    public ResponseEntity<SuccessResponse> deleteScrap(@PathVariable Long scrapId) {
        scrapService.deleteScrap(scrapId, principalHandler.getUserFromPrincipal());
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_DELETE_SCRAP));
    }

    @PatchMapping("/scraps/{scrapId}")
    public ResponseEntity<SuccessResponse> updateScrapColor(@PathVariable Long scrapId, @RequestBody UpdateScrapRequestDto request) {
        scrapService.updateScrapColor(scrapId, request, principalHandler.getUserFromPrincipal());
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_SCRAP));
    }
}
