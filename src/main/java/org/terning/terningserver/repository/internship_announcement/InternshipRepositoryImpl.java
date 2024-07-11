package org.terning.terningserver.repository.internship_announcement;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.terning.terningserver.domain.InternshipAnnouncement;

import static org.terning.terningserver.domain.QInternshipAnnouncement.internshipAnnouncement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class InternshipRepositoryImpl implements InternshipRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<InternshipAnnouncement> getMostViewedInternship() {
        return jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                .where(
                        internDeadlineGoe(),
                        internCreatedAtAfter()
                ) //지원 마감된 공고 및 30일 보다 오래된 공고 제외
                .orderBy(internshipAnnouncement.viewCount.desc(), internshipAnnouncement.createdAt.desc())
                .fetch();
    }

    @Override
    public Optional<InternshipAnnouncement> findByInternshipId() {
        return jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                .leftjoin(intern)
    }

    private BooleanExpression internDeadlineGoe() {
        return internshipAnnouncement.deadline.goe(LocalDate.now());
    }

    private BooleanExpression internCreatedAtAfter() {
        return internshipAnnouncement.createdAt.after(LocalDate.now().minusDays(30).atStartOfDay());
    }
}
