package org.terning.terningserver.repository.scarp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {
    Boolean existsByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);
}