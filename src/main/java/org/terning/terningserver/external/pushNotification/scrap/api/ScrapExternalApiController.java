package org.terning.terningserver.external.pushNotification.scrap.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.external.pushNotification.scrap.application.usecase.ScrapSyncOrchestrator;
import org.terning.terningserver.external.pushNotification.scrap.dto.response.UnsyncedScrapUsersResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/external/scraps")
public class ScrapExternalApiController {

    private final ScrapSyncOrchestrator scrapSyncOrchestrator;

    @GetMapping("/unsynced")
    public ResponseEntity<UnsyncedScrapUsersResponse> fetchUnsyncedScrapUsers() {
        return ResponseEntity.ok(scrapSyncOrchestrator.readUnsyncedUsers());
    }

    @PostMapping("/sync/result")
    public ResponseEntity<Void> syncScrapsManually() {
        scrapSyncOrchestrator.syncUnsyncedUsers();
        return ResponseEntity.ok().build();
    }
}
