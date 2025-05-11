package org.terning.terningserver.internshipAnnouncement.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;
import org.terning.terningserver.filter.domain.Grade;
import org.terning.terningserver.filter.domain.JobType;
import org.terning.terningserver.filter.domain.WorkingPeriod;


import org.terning.terningserver.user.domain.User;

import java.time.LocalDate;
import java.util.List;

import static org.terning.terningserver.internshipAnnouncement.domain.QInternshipAnnouncement.internshipAnnouncement;
import static org.terning.terningserver.scrap.domain.QScrap.scrap;


@RequiredArgsConstructor
public class InternshipRepositoryImpl implements InternshipRepositoryCustom {

    private static final int INTERNSHIP_CREATED_WITHIN_DAYS = 60;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<InternshipAnnouncement> getMostViewedInternship() {
        return jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                //지원 마감된 공고 및 60일 보다 오래된 공고 제외
                .where(
                        internDeadlineGoe(),
                        internCreatedAtAfter()
                )
                .orderBy(internshipAnnouncement.viewCount.desc(), internshipAnnouncement.createdAt.desc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<InternshipAnnouncement> getMostScrappedInternship() {
        return jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                .where(
                        internDeadlineGoe(),
                        internCreatedAtAfter()
                ) //지원 마감된 공고 및 60일 보다 오래된 공고 제외
                .orderBy(internshipAnnouncement.scrapCount.desc(), internshipAnnouncement.createdAt.desc())
                .limit(5)
                .fetch();
    }


    @Override
    public Page<InternshipAnnouncement> searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable) {
        keyword = keyword.toLowerCase();

        List<InternshipAnnouncement> internshipAnnouncements = jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                .where(contentLike(keyword, isPureEnglish(keyword)))
                .orderBy(sortAnnouncementsByDeadline().asc(), createOrderSpecifier(sortBy))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(internshipAnnouncement.count())
                .from(internshipAnnouncement)
                .where(contentLike(keyword, isPureEnglish(keyword)));


        return PageableExecutionUtils.getPage(internshipAnnouncements, pageable, count::fetchOne);
    }


    private boolean isPureEnglish(String summonerName) {
        //공백은 무시
        return summonerName.replaceAll("\\s", "").matches("^[a-zA-Z]+$");
    }

    private BooleanExpression contentLike(String keyword, boolean isPureEnglish) {
        if(isPureEnglish) {
            return Expressions.stringTemplate("LOWER({0})", internshipAnnouncement.title).contains(keyword);
        } else {
            keyword = keyword.replaceAll("\\s", "");
            return Expressions.stringTemplate("REPLACE(LOWER({0}), ' ', '')", internshipAnnouncement.title).contains(keyword);
        }
    }

    //정렬 조건(5가지, 채용 마감 이른 순, 짧은 근무 기간 순, 긴 근무 기간 순,
    private OrderSpecifier createOrderSpecifier(String sortBy) {
        System.out.println("sortBy = " + sortBy);
        return switch (sortBy) {
            case "mostViewed" -> internshipAnnouncement.viewCount.desc();
            case "shortestDuration" -> getWorkingPeriodAsNumber().asc();
            case "longestDuration" -> getWorkingPeriodAsNumber().desc();
            case "mostScrapped" -> internshipAnnouncement.scrapCount.desc();
            case null, default -> internshipAnnouncement.deadline.asc();
        };
    }

    @Override
    public Page<Tuple> findAllInternshipsWithScrapInfo(User user, String sortBy, Pageable pageable) {
        List<Tuple> content = jpaQueryFactory
                .select(internshipAnnouncement, scrap.id, scrap.color)
                .from(internshipAnnouncement)
                .leftJoin(internshipAnnouncement.scraps, scrap).on(scrap.user.eq(user))
                .orderBy(
                        sortAnnouncementsByDeadline().asc(),
                        getSortOrder(sortBy)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(internshipAnnouncement.count())
                .from(internshipAnnouncement);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<Tuple> findFilteredInternshipsWithScrapInfo(User user, String sortBy, Pageable pageable) {
        List<Tuple> content = jpaQueryFactory
                .select(internshipAnnouncement, scrap.id, scrap.color)
                .from(internshipAnnouncement)
                .leftJoin(internshipAnnouncement.scraps, scrap).on(scrap.user.eq(user))
                .where(
                        getJobTypeFilter(user),
                        getGraduatingFilter(user),
                        getWorkingPeriodFilter(user),
                        getStartDateFilter(user)
                )
                .orderBy(
                        sortAnnouncementsByDeadline().asc(),
                        getSortOrder(sortBy)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(internshipAnnouncement.count())
                .from(internshipAnnouncement)
                .where(
                        getJobTypeFilter(user),
                        getGraduatingFilter(user),
                        getWorkingPeriodFilter(user),
                        getStartDateFilter(user)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression getGraduatingFilter(User user){
        if(user.getFilter().getGrade() == null) return null;
        if(user.getFilter().getGrade() != Grade.SENIOR){
            return internshipAnnouncement.isGraduating.isFalse();
        }
        return null;
    }

    private BooleanExpression getWorkingPeriodFilter(User user){
        if(user.getFilter().getWorkingPeriod() == null) return null;
        if(user.getFilter().getWorkingPeriod() == WorkingPeriod.OPTION1){
            return getWorkingPeriodAsNumber().between(1,3);
        } else if(user.getFilter().getWorkingPeriod() == WorkingPeriod.OPTION2){
            return getWorkingPeriodAsNumber().between(4,6);
        } else {
            return getWorkingPeriodAsNumber().goe(7);
        }
    }

    private BooleanExpression getStartDateFilter(User user){
        int startYear = user.getFilter().getStartYear();
        int startMonth = user.getFilter().getStartMonth();

        if(startYear == 0 || startMonth == 0) return null;
        return internshipAnnouncement.startYear.eq(startYear)
                .and(internshipAnnouncement.startMonth.eq(startMonth));
    }

    // 정렬 옵션 (5가지)
    private OrderSpecifier getSortOrder(String sortBy) {
        return switch (sortBy) {
            case "shortestDuration" // 짧은 근무 기간 순
                 -> getWorkingPeriodAsNumber().asc();
            case "longestDuration" // 긴 근무 기간 순
                 -> getWorkingPeriodAsNumber().desc();
            case "mostScrapped" // 스크랩 많은 순
                 -> internshipAnnouncement.scrapCount.desc();
            case "mostViewed" // 조회 수 많은 순
                 -> internshipAnnouncement.viewCount.desc();
            default // 채용 마감 이른 순
                 -> internshipAnnouncement.deadline.asc();
        };
    }

    // String 타입의 workingPeriod를 숫자로 변환하기 위한 메서드(ex. "2개월" => 2)
    private NumberTemplate<Integer> getWorkingPeriodAsNumber() {
        return Expressions.numberTemplate(
                Integer.class,
                "CAST(NULLIF(regexp_replace({0}, '\\D', '', 'g'), '') AS INTEGER)",
                internshipAnnouncement.workingPeriod
        );
    }

    // 지원 마감일이 지나지 않은 공고
    private BooleanExpression internDeadlineGoe() {
        return internshipAnnouncement.deadline.goe(LocalDate.now());
    }

    // 현재 시점으로부터 60일 이내의 공고
    private BooleanExpression internCreatedAtAfter() {
        return internshipAnnouncement.createdAt.after(
                LocalDate.now().minusDays(INTERNSHIP_CREATED_WITHIN_DAYS).atStartOfDay()
        );
    }

    // 서류 마감일이 지난 공고는 가장 아래로 보여주는 로직
    private NumberTemplate<Integer> sortAnnouncementsByDeadline() {
        // 현재 시점보다 마감일이 지나지 않은 경우
        BooleanExpression isNotExpired = internshipAnnouncement.deadline.goe(LocalDate.now());

        // 우선순위를 위한 Expression(지원된 공고는 정렬 우선순위 낮게 -> 밑에 깔리게)
        return Expressions.numberTemplate(
                Integer.class,
                "CASE WHEN {0} THEN 1 ELSE 2 END",
                isNotExpired
        );
    }

    private BooleanExpression getJobTypeFilter(User user) {
        if (user.getFilter().getJobType() == null || user.getFilter().getJobType() == JobType.TOTAL) {
            return null; // total일 경우 모든 직무 공고 허용
        }
        return internshipAnnouncement.jobType.eq(user.getFilter().getJobType().getValue());
    }
}
