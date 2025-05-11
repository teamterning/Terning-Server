package org.terning.terningserver.scrap.repository;

import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;
import org.terning.terningserver.scrap.domain.Scrap;

import java.time.LocalDate;
import java.util.List;

public interface ScrapRepositoryCustom {
    Long findScrapIdByInternshipAnnouncementIdAndUserId(Long internshipAnnouncementId, Long userId);

    List<Scrap> findAllByInternshipAndUserId(List<InternshipAnnouncement> internshipAnnouncements, Long userId);

    List<Scrap> findScrapsByUserIdAndDeadlineBetweenOrderByDeadline(Long userId, LocalDate start, LocalDate end);

    List<Scrap> findScrapsByUserIdAndDeadlineOrderByDeadline(Long userId, LocalDate deadline);

    String findColorByInternshipAnnouncementIdAndUserId(Long internshipAnnouncementId, Long userId);

    List<Long> findUserIdsWithUnsyncedScraps();

    List<Scrap> findUnsyncedScrapsByUserIds(List<Long> userIds);
}
