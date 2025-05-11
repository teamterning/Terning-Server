package org.terning.terningserver.external.pushNotification.scrap.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.external.pushNotification.scrap.application.port.UnsyncedScrapUserReader;
import org.terning.terningserver.external.pushNotification.scrap.application.usecase.ReadUnsyncedScrapUsersUseCase;
import org.terning.terningserver.external.pushNotification.scrap.dto.response.UnsyncedScrapUsersResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadUnsyncedScrapUsersService implements ReadUnsyncedScrapUsersUseCase {

    private final UnsyncedScrapUserReader reader;

    @Override
    public UnsyncedScrapUsersResponse read() {
        List<Long> userIds = reader.readUnsyncedUserIds();
        return UnsyncedScrapUsersResponse.of(userIds);
    }
}
