package org.terning.terningserver.internshipAnnouncement.dto.response;

import lombok.Builder;
import org.terning.terningserver.internshipAnnouncement.domain.Company;
import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;
import org.terning.terningserver.common.util.DateUtil;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record InternshipDetailResponseDto(
        String companyImage,
        String dDay,
        String title,
        String workingPeriod,
        boolean isScrapped,
        String color,
        String deadline,
        String startYearMonth,
        int scrapCount,
        int viewCount,
        String company,
        String companyCategory,
        String qualification,
        String jobType,
        String detail,
        String url
) {
    public static InternshipDetailResponseDto of(
            final InternshipAnnouncement announcement,
            final Company company,
            final Long scrapId,
            final String color
    ) {
        return InternshipDetailResponseDto.builder()
                .companyImage(company.getCompanyImage())
                .dDay(DateUtil.convert(announcement.getDeadline()))
                .title(announcement.getTitle())
                .workingPeriod(announcement.getWorkingPeriod())
                .isScrapped(scrapId!=null)
                .color(color)
                .deadline(DateUtil.convertDeadline(announcement.getDeadline()))
                .startYearMonth(announcement.getStartYear() + "년 " + announcement.getStartMonth() + "월")
                .scrapCount(announcement.getScrapCount())
                .viewCount(announcement.getViewCount())
                .company(company.getCompanyInfo())
                .companyCategory(company.getCompanyCategory().getValue())
                .qualification(announcement.getQualifications())
                .jobType(announcement.getJobType())
                .detail(announcement.getDetail())
                .url(announcement.getUrl())
                .build();
    }
}
