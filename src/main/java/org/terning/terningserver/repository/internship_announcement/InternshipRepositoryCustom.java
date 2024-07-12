package org.terning.terningserver.repository.internship_announcement;

import org.terning.terningserver.domain.InternshipAnnouncement;

import java.util.List;
import java.util.Optional;

public interface InternshipRepositoryCustom {
    List<InternshipAnnouncement> getMostViewedInternship();
    List<InternshipAnnouncement> getMostScrappedInternship();
}
