package org.terning.terningserver.external.pushNotification.scrap.application.usecase;

import org.terning.terningserver.external.pushNotification.scrap.dto.response.UnsyncedScrapUsersResponse;

public interface ReadUnsyncedScrapUsersUseCase {
    UnsyncedScrapUsersResponse read();
}
