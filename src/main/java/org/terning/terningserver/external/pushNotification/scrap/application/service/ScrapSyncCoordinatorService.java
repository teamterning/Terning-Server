package org.terning.terningserver.external.pushNotification.scrap.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.external.pushNotification.scrap.application.usecase.ReadUnsyncedScrapUsersUseCase;
import org.terning.terningserver.external.pushNotification.scrap.application.usecase.ScrapSyncOrchestrator;
import org.terning.terningserver.external.pushNotification.scrap.application.usecase.SyncUnsyncedUsersUseCase;
import org.terning.terningserver.external.pushNotification.scrap.dto.response.UnsyncedScrapUsersResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScrapSyncCoordinatorService implements ScrapSyncOrchestrator {

    private final ReadUnsyncedScrapUsersUseCase readUseCase;
    private final SyncUnsyncedUsersUseCase syncUseCase;

    @Override
    public UnsyncedScrapUsersResponse readUnsyncedUsers() {
        return readUseCase.read();
    }

    @Override
    @Transactional
    public void syncUnsyncedUsers() {
        syncUseCase.sync();
    }
}
