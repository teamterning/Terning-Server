package org.terning.terningserver.repository.internship_announcement;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.User;

import java.util.List;

public interface InternshipRepositoryCustom {
    List<InternshipAnnouncement> getMostViewedInternship();

    List<InternshipAnnouncement> getMostScrappedInternship();

    Page<InternshipAnnouncement> searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable);

    Page<Tuple> findFilteredInternshipsWithScrapInfo(User user, String sortBy, Pageable pageable);

    Page<Tuple> findAllInternshipsWithScrapInfo(User user, String sortBy, Pageable pageable);
}
