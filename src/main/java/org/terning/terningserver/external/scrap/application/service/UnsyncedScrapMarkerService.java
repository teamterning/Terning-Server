package org.terning.terningserver.external.scrap.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.external.scrap.application.port.UnsyncedScrapMarker;
import org.terning.terningserver.repository.scrap.ScrapRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnsyncedScrapMarkerService implements UnsyncedScrapMarker {

    private final ScrapRepository scrapRepository;

    @Transactional
    @Override
    public void markAsSynced(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) return;

        scrapRepository.findUnsyncedScrapsByUserIds(userIds)
                .forEach(Scrap::markSynced);
    }
}
