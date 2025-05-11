package org.terning.terningserver.calendar.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MonthlyListResponseDto(
        String deadline,
        List<ScrapDetail> announcements
) {
    @Builder
    public static record ScrapDetail(
            Long internshipAnnouncementId,
            String companyImage,
            String dDay,
            String title,
            String workingPeriod,
            boolean isScrapped,
            String color,
            String deadline,
            String startYearMonth
    ){
        public static ScrapDetail of(final Long internshipAnnouncementId, final String companyImage, final String dDay,
                                     final String title, final String workingPeriod, final boolean isScrapped,
                                     final String color, final String deadline, final String startYearMonth){
            return ScrapDetail.builder()
                    .internshipAnnouncementId(internshipAnnouncementId)
                    .companyImage(companyImage)
                    .dDay(dDay)
                    .title(title)
                    .workingPeriod(workingPeriod)
                    .isScrapped(isScrapped)
                    .color(color)
                    .deadline(deadline)
                    .startYearMonth(startYearMonth)
                    .build();
        }
    }

    public static MonthlyListResponseDto of(String deadline, List<ScrapDetail> announcements){
        return MonthlyListResponseDto.builder()
                .deadline(deadline)
                .announcements(announcements)
                .build();
    }
}
