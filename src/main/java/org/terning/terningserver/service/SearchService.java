package org.terning.terningserver.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
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

}
