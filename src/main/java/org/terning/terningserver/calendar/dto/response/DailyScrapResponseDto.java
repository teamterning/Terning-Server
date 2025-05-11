package org.terning.terningserver.calendar.dto.response;

import lombok.Builder;
import org.terning.terningserver.scrap.domain.Scrap;
import org.terning.terningserver.common.util.DateUtil;

@Builder
public record DailyScrapResponseDto(
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
    public static DailyScrapResponseDto of(final Scrap scrap){
        String startYearMonth = scrap.getInternshipAnnouncement().getStartYear() + "년 " + scrap.getInternshipAnnouncement().getStartMonth() + "월";
        String deadline = DateUtil.convertDeadline(scrap.getInternshipAnnouncement().getDeadline());

        return DailyScrapResponseDto.builder()
                .internshipAnnouncementId(scrap.getInternshipAnnouncement().getId())
                .companyImage(scrap.getInternshipAnnouncement().getCompany().getCompanyImage())
                .dDay(DateUtil.convert(scrap.getInternshipAnnouncement().getDeadline()))
                .title(scrap.getInternshipAnnouncement().getTitle())
                .workingPeriod(scrap.getInternshipAnnouncement().getWorkingPeriod())
                .isScrapped(true) // 스크랩된 경우에만 DTO 생성하므로 true
                .color(scrap.getColorToHexValue())
                .deadline(deadline)
                .startYearMonth(startYearMonth)
                .build();
    }
}
