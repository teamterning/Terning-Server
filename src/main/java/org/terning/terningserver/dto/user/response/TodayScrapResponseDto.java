package org.terning.terningserver.dto.user.response;

import lombok.Builder;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.util.DateUtil;

@Builder
public record TodayScrapResponseDto(
        Long scrapId,
        Long internshipAnnouncementId,
        String companyImage,
        String title,
        String dDay,
        String deadline,
        String workingPeriod,
        String startYearMonth,
        String color
) {
    public static TodayScrapResponseDto of(final Scrap scrap){
        String startYearMonth = scrap.getInternshipAnnouncement().getStartYear() + "년 " + scrap.getInternshipAnnouncement().getStartMonth() + "월";

        return TodayScrapResponseDto.builder()
                .scrapId(scrap.getId())
                .internshipAnnouncementId(scrap.getInternshipAnnouncement().getId())
                .companyImage(scrap.getInternshipAnnouncement().getCompany().getCompanyImage())
                .title(scrap.getInternshipAnnouncement().getTitle())
                .dDay(DateUtil.convert(scrap.getInternshipAnnouncement().getDeadline()))
                .deadline(DateUtil.convertDeadline(scrap.getInternshipAnnouncement().getDeadline()))
                .workingPeriod(scrap.getInternshipAnnouncement().getWorkingPeriod())
                .startYearMonth(startYearMonth)
                .color(scrap.getColor().getColorValue())
                .build();
    }
}
