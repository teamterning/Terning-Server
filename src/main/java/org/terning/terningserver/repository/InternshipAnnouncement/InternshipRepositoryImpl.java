package org.terning.terningserver.repository.InternshipAnnouncement;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.terning.terningserver.domain.InternshipAnnouncement;

import static org.terning.terningserver.domain.QInternshipAnnouncement.internshipAnnouncement;

import java.time.LocalDate;
import java.util.List;

public class InternshipRepositoryImpl implements InternshipRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<InternshipAnnouncement> getMostViewedInternship() {
        return jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                .where(
                        internDeadlineGoe(),
                        internCreatedAtAfter()
                ) //지원 마감된 공고 및 30일 보다 오래된 공고 제외
                .orderBy(internshipAnnouncement.scrapCount.desc(), internshipAnnouncement.createdAt.desc())
                .fetch();
    }

    private BooleanExpression internDeadlineGoe() {
        return internshipAnnouncement.deadline.goe(LocalDate.now());
    }

    private BooleanExpression internCreatedAtAfter() {
        return internshipAnnouncement.createdAt.after(LocalDate.now().minusDays(30).atStartOfDay());
    }
}
