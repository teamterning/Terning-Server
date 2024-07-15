package org.terning.terningserver.dto.calendar.response;

import lombok.Builder;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.util.DateUtil;

@Builder
public record DailyScrapResponseDto(
        Long scrapId,
        Long internshipAnnouncementId,
        String title,
        String dDay,
        String workingPeriod,
        String color,
        String companyImage,
        int startYear,
        int startMonth
) {
    public static DailyScrapResponseDto of(final Scrap scrap){
        return DailyScrapResponseDto.builder()
                .scrapId(scrap.getId())
                .internshipAnnouncementId(scrap.getInternshipAnnouncement().getId())
                .title(scrap.getInternshipAnnouncement().getTitle())
                .dDay(DateUtil.convert(scrap.getInternshipAnnouncement().getDeadline()))
                .workingPeriod(scrap.getInternshipAnnouncement().getWorkingPeriod())
                .color(scrap.getColor().getColorValue())
                .companyImage(scrap.getInternshipAnnouncement().getCompany().getCompanyImage())
                .startYear(scrap.getInternshipAnnouncement().getStartYear())
                .startMonth(scrap.getInternshipAnnouncement().getStartMonth())
                .build();
    }
}
