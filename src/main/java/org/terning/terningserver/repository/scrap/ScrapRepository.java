package org.terning.terningserver.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.Scrap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Boolean existsByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);
    List<Scrap> findByUserIdAndInternshipAnnouncement_Deadline(Long userId, LocalDate deadline);
}
