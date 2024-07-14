package org.terning.terningserver.repository.internship_announcement;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.enums.Grade;
import org.terning.terningserver.domain.enums.WorkingPeriod;

import static org.terning.terningserver.domain.QInternshipAnnouncement.internshipAnnouncement;
import static org.terning.terningserver.domain.QUser.user;


import java.time.LocalDate;
import java.util.List;

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
    public List<InternshipAnnouncement> getMostScrappedInternship() {
        return jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                .where(
                        internDeadlineGoe(),
                        internCreatedAtAfter()
                ) //지원 마감된 공고 및 30일 보다 오래된 공고 제외
                .orderBy(internshipAnnouncement.scrapCount.desc(), internshipAnnouncement.createdAt.desc())
                .fetch();
    }

    @Override
    public Page<InternshipAnnouncement> searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable) {
        LocalDate today = LocalDate.now();

        // 현재 시점보다 마감일이 지나지 않은 경우
        BooleanExpression isNotExpired = internshipAnnouncement.deadline.goe(today);

        // 우선순위를 위한 Expression(지원된 공고는 정렬 우선순위 낮게 -> 밑에 깔리게)
        NumberTemplate<Integer> priority = Expressions.numberTemplate(
                Integer.class,
                "CASE WHEN {0} THEN 1 ELSE 2 END",
                isNotExpired
        );

        List<InternshipAnnouncement> internshipAnnouncements = jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                .leftJoin(internshipAnnouncement.scraps).fetchJoin()
                .where(contentLike(keyword))
                .orderBy(priority.asc(), createOrderSpecifier(sortBy))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count =jpaQueryFactory
                .select(internshipAnnouncement.count())
                .from(internshipAnnouncement)
                .leftJoin(internshipAnnouncement.scraps)
                .where(contentLike(keyword))
                .fetchOne();


        return new PageImpl<>(internshipAnnouncements, pageable, count);
    }

    private BooleanExpression contentLike(String keyword) {
        return internshipAnnouncement.title.contains(keyword);
    }


    //정렬 조건(5가지, 채용 마감 이른 순, 짧은 근무 기간 순, 긴 근무 기간 순,
    private OrderSpecifier createOrderSpecifier(String sortBy) {
        return switch (sortBy) {
            case "mostViewed" -> internshipAnnouncement.viewCount.desc();
            case "shortestDuration" -> getWorkingPeriodAsNumber().asc();
            case "longestDuration" -> getWorkingPeriodAsNumber().desc();
            case "mostScrapped" -> internshipAnnouncement.scrapCount.desc();
            default -> internshipAnnouncement.deadline.asc();
        };
    }

    /**
     * String 타입의 workingPeriod를 숫자로 정렬하게 하기 위한 메서드
     * @return
     */
    private NumberTemplate<Integer> getWorkingPeriodAsNumber() {
        return Expressions.numberTemplate(
                Integer.class,
                "CAST(SUBSTRING({0}, 1, LENGTH({0}) - 2) AS INTEGER)",
                internshipAnnouncement.workingPeriod
        );
    }

    //지원 마감일이 지나지 않은 공고
    private BooleanExpression internDeadlineGoe() {
        return internshipAnnouncement.deadline.goe(LocalDate.now());
    }

    // 현재 시점으로부터 30일 이내의 공고
    private BooleanExpression internCreatedAtAfter() {
        return internshipAnnouncement.createdAt.after(LocalDate.now().minusDays(30).atStartOfDay());
    }
}
