package org.terning.terningserver.scrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.scrap.domain.Scrap;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {
    Boolean existsByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);

    Optional<Scrap> findByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);

    void deleteByInternshipAnnouncementIdAndUserId(Long internshipId, Long userId);

    boolean existsByUserId(Long userId);
}

