package org.terning.terningserver.repository.internship_announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.InternshipAnnouncement;

public interface InternshipRepository extends JpaRepository<InternshipAnnouncement, Long>, InternshipRepositoryCustom {

}

