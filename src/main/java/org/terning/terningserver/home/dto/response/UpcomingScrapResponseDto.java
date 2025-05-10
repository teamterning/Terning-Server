package org.terning.terningserver.home.dto.response;

import lombok.Builder;
import org.terning.terningserver.scrap.domain.Scrap;
import org.terning.terningserver.common.util.DateUtil;

import java.util.List;

@Builder
public record UpcomingScrapResponseDto(
        boolean hasScrapped,
        List<ScrapDetail> scraps
) {
    @Builder
    public record ScrapDetail(
            Long internshipAnnouncementId,
            String companyImage,
            String dDay,
            String title,
            String workingPeriod,
            boolean isScrapped,
            String color,
            String deadline,
            String startYearMonth,
            String companyInfo
    ) {
        public static ScrapDetail of(final Scrap scrap) {
            String startYearMonth = scrap.getInternshipAnnouncement().getStartYear() + "년 " + scrap.getInternshipAnnouncement().getStartMonth() + "월";

            return ScrapDetail.builder()
                    .internshipAnnouncementId(scrap.getInternshipAnnouncement().getId())
                    .companyImage(scrap.getInternshipAnnouncement().getCompany().getCompanyImage())
                    .dDay(DateUtil.convert(scrap.getInternshipAnnouncement().getDeadline()))
                    .title(scrap.getInternshipAnnouncement().getTitle())
                    .deadline(DateUtil.convertDeadline(scrap.getInternshipAnnouncement().getDeadline()))
                    .isScrapped(true) // 스크랩된 항목이므로 항상 true
                    .color(scrap.getColorToHexValue())
                    .workingPeriod(scrap.getInternshipAnnouncement().getWorkingPeriod())
                    .startYearMonth(startYearMonth)
                    .companyInfo(scrap.getInternshipAnnouncement().getCompany().getCompanyInfo())
                    .build();
        }
    }
}
