package org.terning.terningserver.dto.user.response;

import lombok.Builder;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.util.DateUtil;

@Builder
public record UpcomingScrapResponseDto(
        Long internshipAnnouncementId,
        String companyImage,
        String dDay,
        String title,
        String workingPeriod,
        boolean isScrapped,
        String color,
        String deadline,
        String startYearMonth
) {
    public static UpcomingScrapResponseDto of(final Scrap scrap){
        String startYearMonth = scrap.getInternshipAnnouncement().getStartYear() + "년 " + scrap.getInternshipAnnouncement().getStartMonth() + "월";

        return UpcomingScrapResponseDto.builder()
                .internshipAnnouncementId(scrap.getInternshipAnnouncement().getId())
                .companyImage(scrap.getInternshipAnnouncement().getCompany().getCompanyImage())
                .dDay(DateUtil.convert(scrap.getInternshipAnnouncement().getDeadline()))
                .title(scrap.getInternshipAnnouncement().getTitle())
                .deadline(DateUtil.convertDeadline(scrap.getInternshipAnnouncement().getDeadline()))
                .isScrapped(true) // 스크랩된 항목이므로 항상 true
                .color(scrap.getColorToHexValue())
                .workingPeriod(scrap.getInternshipAnnouncement().getWorkingPeriod())
                .startYearMonth(startYearMonth)
                .build();
    }
}
