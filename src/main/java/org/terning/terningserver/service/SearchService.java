package org.terning.terningserver.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponse;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final InternshipRepository internshipRepository;

    public PopularAnnouncementListResponse getMostViewedAnnouncements() {
        List<InternshipAnnouncement> mostViewedInternships = internshipRepository.getMostViewedInternship();
        return PopularAnnouncementListResponse.of(mostViewedInternships);
    }

    public PopularAnnouncementListResponse getMostScrappedAnnouncements() {
        List<InternshipAnnouncement> mostViewedInternships = internshipRepository.getMostScrappedInternship();
        return PopularAnnouncementListResponse.of(mostViewedInternships);
    }

    public List<InternshipAnnouncement> searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable) {
        List<InternshipAnnouncement> announcements = internshipRepository.searchInternshipAnnouncement(keyword, sortBy, pageable);
        announcements.stream().map(announcement ->
    }

}
