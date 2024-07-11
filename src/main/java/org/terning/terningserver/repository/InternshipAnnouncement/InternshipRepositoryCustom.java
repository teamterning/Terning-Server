package org.terning.terningserver.repository.InternshipAnnouncement;

import org.terning.terningserver.domain.InternshipAnnouncement;

import java.util.List;

public interface InternshipRepositoryCustom {
    List<InternshipAnnouncement> getMostViewedInternship();
}
