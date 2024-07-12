package org.terning.terningserver.repository.internship_announcement;

import org.springframework.data.domain.Pageable;
import org.terning.terningserver.domain.InternshipAnnouncement;

import java.util.List;

public interface InternshipRepositoryCustom {
    List<InternshipAnnouncement> getMostViewedInternship();

    List<InternshipAnnouncement> getMostScrappedInternship();

    List<InternshipAnnouncement> searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable);
}
