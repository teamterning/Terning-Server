package org.terning.terningserver.repository.internship_announcement;

import org.terning.terningserver.domain.InternshipAnnouncement;

import java.awt.print.Pageable;
import java.util.List;

public interface InternshipRepositoryCustom {
    List<InternshipAnnouncement> getMostViewedInternship();

    List<InternshipAnnouncement> getMostScrappedInternship();

    List<InternshipAnnouncement> searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable);
}
