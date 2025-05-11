package org.terning.terningserver.internshipAnnouncement.repository;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;

import java.util.Optional;

public interface InternshipRepository extends JpaRepository<InternshipAnnouncement, Long>, InternshipRepositoryCustom {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<InternshipAnnouncement> findById(Long aLong);
}

