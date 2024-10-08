package org.terning.terningserver.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.Scrap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {
    Boolean existsByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);

    Optional<Scrap> findByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);

    void deleteByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);

    boolean existsByUserId(Long userId);

}

