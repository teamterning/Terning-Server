package org.terning.terningserver.service;


import org.springframework.data.domain.Pageable;
import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponse;
import org.terning.terningserver.dto.search.response.SearchResultResponse;

public interface SearchService {

    PopularAnnouncementListResponse getMostViewedAnnouncements();

    PopularAnnouncementListResponse getMostScrappedAnnouncements();

    SearchResultResponse searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable);

}
