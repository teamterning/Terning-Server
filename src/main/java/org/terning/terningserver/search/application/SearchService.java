package org.terning.terningserver.search.application;

import org.terning.terningserver.search.dto.response.PopularAnnouncementListResponseDto;
import org.springframework.data.domain.Pageable;
import org.terning.terningserver.search.dto.response.SearchResultResponseDto;

public interface SearchService {

    PopularAnnouncementListResponseDto getMostViewedAnnouncements();

    PopularAnnouncementListResponseDto getMostScrappedAnnouncements();

    SearchResultResponseDto searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable, Long userId);

}