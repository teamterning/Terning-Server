package org.terning.terningserver.external.pushNotification.scrap.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.terning.terningserver.external.pushNotification.scrap.application.service.ScrapSyncCoordinatorService;

@Component
@RequiredArgsConstructor
public class ScrapSyncScheduler {

    private final ScrapSyncCoordinatorService scrapSyncCoordinatorService;

    @Scheduled(cron = "0 30 21 * * *")
    public void syncScraps() {
        scrapSyncCoordinatorService.syncUnsyncedUsers();
    }
}
