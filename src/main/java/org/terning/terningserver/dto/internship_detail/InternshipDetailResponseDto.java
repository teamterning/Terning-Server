package org.terning.terningserver.dto.internship_detail;

import lombok.Builder;
import org.terning.terningserver.domain.Company;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.util.DateUtil;

@Builder
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
    public static InternshipDetailResponseDto of(InternshipAnnouncement announcement, Company company, Long scrapId, String color) {
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
