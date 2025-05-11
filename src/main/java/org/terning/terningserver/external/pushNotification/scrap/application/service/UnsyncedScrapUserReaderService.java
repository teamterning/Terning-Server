package org.terning.terningserver.external.pushNotification.scrap.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.external.pushNotification.scrap.application.port.UnsyncedScrapUserReader;
import org.terning.terningserver.scrap.repository.ScrapRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnsyncedScrapUserReaderService implements UnsyncedScrapUserReader {

    private final ScrapRepository scrapRepository;

    @Override
    public List<Long> readUnsyncedUserIds() {
        return scrapRepository.findUserIdsWithUnsyncedScraps();
    }
}



