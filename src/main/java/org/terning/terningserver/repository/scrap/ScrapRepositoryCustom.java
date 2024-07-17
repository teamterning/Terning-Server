package org.terning.terningserver.repository.scrap;

import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;

import java.util.List;

public interface ScrapRepositoryCustom {
    Long findScrapIdByInternshipAnnouncementIdAndUserId(Long internshipAnnouncementId, Long userId);

    List<Scrap> findAllByInternshipAndUserId(List<InternshipAnnouncement> internshipAnnouncements, Long userId);

}
