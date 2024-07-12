package org.terning.terningserver.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponse;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;

import java.awt.print.Pageable;
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

    public void searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable) {
        internshipRepository.searchInternshipAnnouncement(keyword, sortBy, pageable);
    }

}
