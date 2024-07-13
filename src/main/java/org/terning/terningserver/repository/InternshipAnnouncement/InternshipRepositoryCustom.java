package org.terning.terningserver.repository.InternshipAnnouncement;

import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.User;
import java.util.List;

public interface InternshipRepositoryCustom {
  
    List<InternshipAnnouncement> getMostViewedInternship();

    List<InternshipAnnouncement> getMostScrappedInternship();
  
    List<InternshipAnnouncement> findFilteredInternships(User user, String sortBy, int startYear, int startMonth);
  
}
