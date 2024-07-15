package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.service.ScrapService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/scraps/{internshipAnnouncementId}")
    public void createScrap(@PathVariable Long internshipAnnouncementId, @RequestBody CreateScrapRequestDto request) {
        scrapService.createScrap(internshipAnnouncementId, request);
    }
}
