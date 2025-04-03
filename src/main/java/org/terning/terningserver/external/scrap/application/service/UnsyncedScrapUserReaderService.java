package org.terning.terningserver.external.scrap.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.external.scrap.application.port.UnsyncedScrapUserReader;
import org.terning.terningserver.repository.scrap.ScrapRepository;

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



