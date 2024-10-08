package org.terning.terningserver.repository.internship_announcement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.User;

import java.util.List;

public interface InternshipRepositoryCustom {
    List<InternshipAnnouncement> getMostViewedInternship();

    List<InternshipAnnouncement> getMostScrappedInternship();

    Page<InternshipAnnouncement> searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable);

    List<InternshipAnnouncement> findFilteredInternships(User user, String sortBy, int startYear, int startMonth);

}
