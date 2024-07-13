package org.terning.terningserver.repository.InternshipAnnouncement;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.QInternshipAnnouncement;
import org.terning.terningserver.domain.QUser;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.Grade;
import org.terning.terningserver.domain.enums.WorkingPeriod;
import org.terning.terningserver.util.DateUtil;

import static org.terning.terningserver.domain.QInternshipAnnouncement.internshipAnnouncement;
import static org.terning.terningserver.domain.QScrap.scrap;
import static org.terning.terningserver.domain.QUser.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
                .orderBy(internshipAnnouncement.scrapCount.desc(), internshipAnnouncement.createdAt.desc())
                .fetch();
    }

    private BooleanExpression internDeadlineGoe() {
        return internshipAnnouncement.deadline.goe(LocalDate.now());
    }

    private BooleanExpression internCreatedAtAfter() {
        return internshipAnnouncement.createdAt.after(LocalDate.now().minusDays(30).atStartOfDay());
    }

    @Override
    public List<InternshipAnnouncement> findFilteredInternships(User user, String sortBy, int startYear, int startMonth){
        return jpaQueryFactory
                .selectFrom(internshipAnnouncement)
                .leftJoin(internshipAnnouncement.scrapList, scrap).on(scrap.user.eq(user))
                .where(
                        getGraduatingFilter(user),
                        getWorkingPeriodFilter(user),
                        getStartDateFilter(startYear, startMonth)
                )
                .orderBy(
                        priority.asc(),
                        getSortOrder(sortBy)
                )
                .fetch();
    }

    private BooleanExpression getGraduatingFilter(User user){
        if(user.getFilter().getGrade() != Grade.SENIOR){
            return internshipAnnouncement.isGraduating.isFalse();
        }
        return null;
    }

    private BooleanExpression getWorkingPeriodFilter(User user){
        if(user.getFilter().getWorkingPeriod() == WorkingPeriod.OPTION1){
            return getWorkingPeriodAsNumber().between(1,3);
        } else if(user.getFilter().getWorkingPeriod() == WorkingPeriod.OPTION2){
            return getWorkingPeriodAsNumber().between(4,6);
        } else {
            return getWorkingPeriodAsNumber().goe(7);
        }
    }

    private BooleanExpression getStartDateFilter(int startYear, int startMonth){
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

    // 문자열 -> 숫자로 변경(순서 비교)
    private NumberTemplate<Integer> getWorkingPeriodAsNumber(){
        return Expressions.numberTemplate(
                Integer.class,
                "CAST(SUBSTRING({0}, 1, LENGTH({0}) - 2) AS INTEGER)",
                internshipAnnouncement.workingPeriod
        );
    }


    //서류 마감일이 지난 공고는 가장 아래로 보여주는 로직
    BooleanExpression isNotExpired = internshipAnnouncement.deadline.goe(LocalDate.now());

    NumberTemplate<Integer> priority = Expressions.numberTemplate(
                Integer.class,
                "CASE WHEN {0} THEN 1 ELSE 2 END",
                isNotExpired
                );

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

    //지원 마감일이 지나지 않은 공고
    private BooleanExpression internDeadlineGoe() {
        return internshipAnnouncement.deadline.goe(LocalDate.now());
    }

    // 현재 시점으로부터 30일 이내의 공고
    private BooleanExpression internCreatedAtAfter() {
        return internshipAnnouncement.createdAt.after(LocalDate.now().minusDays(30).atStartOfDay());
    }

}
