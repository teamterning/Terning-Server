package org.terning.terningserver.external.scrap.application;

import org.terning.terningserver.external.scrap.dto.response.UnsyncedScrapUsersResponse;

public interface UnsyncedScrapUserReader {
    UnsyncedScrapUsersResponse readUnsyncedScrapUsers();
}
