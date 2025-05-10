package org.terning.terningserver.external.pushNotification.scrap.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.external.pushNotification.scrap.application.port.ScrapSyncNotifier;
import org.terning.terningserver.external.pushNotification.scrap.application.port.UnsyncedScrapMarker;
import org.terning.terningserver.external.pushNotification.scrap.application.port.UnsyncedScrapUserReader;
import org.terning.terningserver.external.pushNotification.scrap.application.usecase.SyncUnsyncedUsersUseCase;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncUnsyncedUsersService implements SyncUnsyncedUsersUseCase {

    private final UnsyncedScrapUserReader reader;
    private final ScrapSyncNotifier notifier;
    private final UnsyncedScrapMarker marker;

    @Override
    @Transactional
    public void sync() {
        List<Long> userIds = reader.readUnsyncedUserIds();
        if (userIds.isEmpty()) return;

        notifier.notify(userIds);
        marker.markAsSynced(userIds);
    }
}
