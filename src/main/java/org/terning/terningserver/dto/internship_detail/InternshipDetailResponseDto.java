package org.terning.terningserver.dto.internship_detail;

import lombok.Builder;
import org.terning.terningserver.domain.Company;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.util.DateUtil;

@Builder
public record InternshipDetailResponseDto(
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
        Long scrapId
) {
    public static InternshipDetailResponseDto of(InternshipAnnouncement announcement, Company company, Long scrapId) {
        return InternshipDetailResponseDto.builder()
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
                .scrapId(scrapId)
                .build();
    }
}
