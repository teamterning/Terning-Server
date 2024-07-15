package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.exception.enums.SuccessMessage;
import org.terning.terningserver.service.ScrapService;

import static org.terning.terningserver.exception.enums.SuccessMessage.SUCCESS_CREATE_SCRAP;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/scraps/{internshipAnnouncementId}")
    public ResponseEntity<SuccessResponse> createScrap(@PathVariable Long internshipAnnouncementId, @RequestBody CreateScrapRequestDto request) {
        scrapService.createScrap(internshipAnnouncementId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_CREATE_SCRAP));
    }
}
