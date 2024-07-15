package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.service.ScrapService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/scraps/{internshipAnnouncementId}")
    public void createScrap(@PathVariable Long internshipAnnouncementId, @RequestBody CreateScrapRequest request) {
        scrapService.
    }
}
