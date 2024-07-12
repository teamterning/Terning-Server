package org.terning.terningserver.repository.internship_announcement;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.terning.terningserver.domain.InternshipAnnouncement;

import static org.terning.terningserver.domain.QInternshipAnnouncement.internshipAnnouncement;

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
    public List<InternshipAnnouncement> searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable) {
        LocalDate today = LocalDate.now();

        // 현재 시점보다 마감일이 지난 경우
        BooleanExpression isExpired = internshipAnnouncement.deadline.before(today);

        // 현재 시점보다 마감일이 지나지 않은 경우
        BooleanExpression isNotExpired = internshipAnnouncement.deadline.goe(today);

        // 우선순위를 위한 Expression(지원된 공고는 정렬 우선순위 낮게 -> 밑에 깔리게)
        NumberTemplate<Integer> priority = Expressions.numberTemplate(
                Integer.class,
                "CASE WHEN {0} THEN 1 ELSE 2 END",
                isNotExpired
        );

        return jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                .leftJoin(internshipAnnouncement.scraps).fetchJoin()
                .orderBy(priority.asc(), createOrderSpecifier(sortBy))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private OrderSpecifier createOrderSpecifier(String sortBy) {
        return switch (sortBy) {
            case "mostViewed" -> new OrderSpecifier<>(Order.DESC, internshipAnnouncement.viewCount);
            case "shortestDuration" -> new OrderSpecifier<>(Order.ASC, getWorkingPeriodAsNumber());
            case "longestDuration" -> new OrderSpecifier<>(Order.DESC, getWorkingPeriodAsNumber());
            case "mostScrapped" -> new OrderSpecifier<>(Order.DESC, internshipAnnouncement.scrapCount);
            default -> new OrderSpecifier<>(Order.ASC, internshipAnnouncement.deadline);
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
