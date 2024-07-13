package org.terning.terningserver.repository.scarp;

import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;

import java.util.List;

public interface ScrapRepositoryCustom {

    List<Scrap> findAllByInternshipAndUserId(List<InternshipAnnouncement> internshipAnnouncements, Long userId);

}
