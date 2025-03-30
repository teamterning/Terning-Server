package org.terning.terningserver.repository.scrap;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;

import java.time.LocalDate;
import java.util.List;

import static org.terning.terningserver.domain.QScrap.scrap;

@RequiredArgsConstructor
public class ScrapRepositoryImpl implements ScrapRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Scrap> findAllByInternshipAndUserId(List<InternshipAnnouncement> internshipAnnouncements, Long userId) {
        return jpaQueryFactory
                .selectFrom(scrap)
                .where(scrap.internshipAnnouncement.in(internshipAnnouncements), scrap.user.id.eq(userId))
                .fetch();
    }

    @Override
    public Long findScrapIdByInternshipAnnouncementIdAndUserId(Long internshipAnnouncementId, Long userId) {
        return jpaQueryFactory
                .select(scrap.id)
                .from(scrap)
                .where(scrap.internshipAnnouncement.id.eq(internshipAnnouncementId)
                        .and(scrap.user.id.eq(userId)))
                .fetchOne();
    }

    @Override
    public List<Scrap> findScrapsByUserIdAndDeadlineBetweenOrderByDeadline(Long userId, LocalDate start, LocalDate end){
        return jpaQueryFactory
                .selectFrom(scrap)
                .where(scrap.user.id.eq(userId)
                        .and(scrap.internshipAnnouncement.deadline.between(start, end)))
                .orderBy(scrap.internshipAnnouncement.deadline.asc())
                .fetch();
    }

    @Override
    public List<Scrap> findScrapsByUserIdAndDeadlineOrderByDeadline(Long userId, LocalDate deadline){
        return jpaQueryFactory
                .selectFrom(scrap)
                .where(scrap.user.id.eq(userId)
                        .and(scrap.internshipAnnouncement.deadline.eq(deadline)))
                .orderBy(scrap.internshipAnnouncement.deadline.asc())
                .fetch();
    }

    @Override
    public String findColorByInternshipAnnouncementIdAndUserId(Long internshipAnnouncementId, Long userId){
        Scrap foundScrap = jpaQueryFactory
                .selectFrom(scrap)
                .where(scrap.internshipAnnouncement.id.eq(internshipAnnouncementId)
                        .and(scrap.user.id.eq(userId)))
                .fetchOne();

        return foundScrap != null ? foundScrap.getColorToHexValue() : null;
    }

    @Override
    public List<Long> findUserIdsWithUnsyncedScraps() {
        return jpaQueryFactory
                .select(scrap.user.id)
                .from(scrap)
                .where(scrap.syncStatus.value.eq(false))
                .distinct()
                .fetch();
    }
}
