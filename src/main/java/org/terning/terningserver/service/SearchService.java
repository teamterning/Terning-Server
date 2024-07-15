package org.terning.terningserver.service;

import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponseDto;
import org.springframework.data.domain.Pageable;
import org.terning.terningserver.dto.search.response.SearchResultResponse;

public interface SearchService {

    PopularAnnouncementListResponseDto getMostViewedAnnouncements();

    PopularAnnouncementListResponseDto getMostScrappedAnnouncements();

    SearchResultResponse searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable);

}