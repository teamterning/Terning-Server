package org.terning.terningserver.external.scrap.application.usecase;

import org.terning.terningserver.external.scrap.dto.response.UnsyncedScrapUsersResponse;

public interface ScrapSyncOrchestrator {
    UnsyncedScrapUsersResponse readUnsyncedUsers();
    void syncUnsyncedUsers();
}
