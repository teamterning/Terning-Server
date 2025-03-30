package org.terning.terningserver.external.scrap.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.external.scrap.dto.response.UnsyncedScrapUsersResponse;

@Service
@RequiredArgsConstructor
public class ScrapExternalService {

    private final UnsyncedScrapUserReader unsyncedScrapUserReader;

    public UnsyncedScrapUsersResponse fetchUnsyncedScrapUsers() {
        return unsyncedScrapUserReader.readUnsyncedScrapUsers();
    }
}

