package org.terning.terningserver.external.scrap.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.external.scrap.application.ScrapExternalService;
import org.terning.terningserver.external.scrap.dto.response.UnsyncedScrapUsersResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/external/scraps")
public class ScrapExternalApiController {

    private final ScrapExternalService scrapExternalService;

    @GetMapping("/unsynced")
    public ResponseEntity<UnsyncedScrapUsersResponse> fetchUnsyncedScrapUsers() {
        UnsyncedScrapUsersResponse response = scrapExternalService.fetchUnsyncedScrapUsers();
        return ResponseEntity.ok(response);
    }
}
