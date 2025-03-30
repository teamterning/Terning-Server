package org.terning.terningserver.external.scrap.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.external.scrap.dto.response.UnsyncedScrapUsersResponse;
import org.terning.terningserver.repository.scrap.ScrapRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnsyncedScrapUserReaderImpl implements UnsyncedScrapUserReader {

    private final ScrapRepository scrapRepository;

    @Override
    public UnsyncedScrapUsersResponse readUnsyncedScrapUsers() {
        List<Long> userIds = scrapRepository.findUserIdsWithUnsyncedScraps();
        return UnsyncedScrapUsersResponse.of(userIds);
    }

}
