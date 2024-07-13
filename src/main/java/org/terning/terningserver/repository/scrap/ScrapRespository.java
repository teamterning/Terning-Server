package org.terning.terningserver.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.Scrap;

public interface ScrapRespository extends JpaRepository<Scrap, Long> {

    Boolean existsByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);
}
