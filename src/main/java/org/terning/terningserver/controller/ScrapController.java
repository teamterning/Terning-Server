package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.ScrapSwagger;
import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.dto.scrap.request.UpdateScrapRequestDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.exception.enums.SuccessMessage;
import org.terning.terningserver.service.ScrapService;

import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_CREATE_SCRAP;
import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_UPDATE_SCRAP;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScrapController implements ScrapSwagger {
    private final ScrapService scrapService;

    @PostMapping("/scraps/{internshipAnnouncementId}")
    public ResponseEntity<SuccessResponse> createScrap(@PathVariable Long internshipAnnouncementId, @RequestBody CreateScrapRequestDto request) {
        scrapService.createScrap(internshipAnnouncementId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_CREATE_SCRAP));
    }

    @DeleteMapping("/scraps/{scrapId}")
    public ResponseEntity<SuccessResponse> deleteScrap(@PathVariable Long scrapId) {
        scrapService.deleteScrap(scrapId);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_CREATE_SCRAP));
    }

    @PatchMapping("/scraps/{scrapId}")
    public ResponseEntity<SuccessResponse> updateScrapColor(@PathVariable Long scrapId, @RequestBody UpdateScrapRequestDto request) {
        scrapService.updateScrapColor(scrapId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_SCRAP));
    }
}
