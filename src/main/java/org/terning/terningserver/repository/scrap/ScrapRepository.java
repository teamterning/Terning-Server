package org.terning.terningserver.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.Scrap;

import java.time.LocalDate;
import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {
    Boolean existsByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);

    List<Scrap> findByUserIdAndInternshipAnnouncement_Deadline(Long userId, LocalDate deadline);

    List<Scrap> findByUserIdAndInternshipAnnouncement_DeadlineBetween(Long userId, LocalDate start, LocalDate end);
}

