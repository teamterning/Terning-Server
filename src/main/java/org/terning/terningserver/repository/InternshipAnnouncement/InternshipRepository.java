package org.terning.terningserver.repository.InternshipAnnouncement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.InternshipAnnouncement;

public interface InternshipRepository extends JpaRepository<InternshipAnnouncement, Long>, InternshipRepositoryCustom {
}