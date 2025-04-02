package org.terning.terningserver.external.scrap.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.terning.terningserver.external.scrap.application.ScrapSyncCoordinatorService;

@Component
@RequiredArgsConstructor
public class ScrapSyncScheduler {

    private final ScrapSyncCoordinatorService scrapSyncUseCase;

    @Scheduled(cron = "0 0 21 * * *")
    public void syncScraps() {
        scrapSyncUseCase.syncScrapsToNotification();
    }
}


