package org.terning.terningserver.dto.internship_detail;

import lombok.Builder;
import org.terning.terningserver.domain.Company;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.util.DateUtil;

@Builder
public record InternshipDetailResponse(
        String dDay,
        String title,
        String deadline,
        String workingPeriod,
        String startDate,
        int scrapCount,
        int viewCount,
        String company,
        String companyCategory,
        String companyImage,
        String qualification,
        String jobType,
        String detail,
        String url,
        boolean isScrapped
) {
    public static InternshipDetailResponse of(InternshipAnnouncement announcement, Company company, boolean isScrapped) {
        return InternshipDetailResponse.builder()
                .dDay(DateUtil.convert(announcement.getDeadline()))
                .title(announcement.getTitle())
                .deadline(DateUtil.convertDeadline(announcement.getDeadline()))
                .workingPeriod(announcement.getWorkingPeriod())
                .startDate(announcement.getStartYear() + "년 " + announcement.getStartMonth() + "월")
                .scrapCount(announcement.getScrapCount())
                .viewCount(announcement.getViewCount())
                .company(company.getCompanyInfo())
                .companyCategory(company.getCompanyCategory().getValue())
                .companyImage(company.getCompanyImage())
                .qualification(announcement.getQualifications())
                .jobType(announcement.getJobType())
                .detail(announcement.getDetail())
                .url(announcement.getUrl())
                .isScrapped(isScrapped)
                .build();
    }
}
