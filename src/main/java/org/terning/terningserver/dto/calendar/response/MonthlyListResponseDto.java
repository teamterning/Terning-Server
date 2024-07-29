package org.terning.terningserver.dto.calendar.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MonthlyListResponseDto(
        String deadline,
        List<ScrapDetail> scraps
) {
    @Builder
    public static record ScrapDetail(
            Long scrapId,
            Long internshipAnnouncementId,
            String title,
            String dDay,
            String workingPeriod,
            String color,
            String companyImage,
            int startYear,
            int startMonth
    ){
        public static ScrapDetail of(final Long scrapId, final Long internshipAnnouncementId, final String title,
                                     final String dDay, final String workingPeriod, final String color,
                                     final String companyImage, final int startYear, final int startMonth){
            return ScrapDetail.builder()
                    .scrapId(scrapId)
                    .internshipAnnouncementId(internshipAnnouncementId)
                    .title(title)
                    .dDay(dDay)
                    .workingPeriod(workingPeriod)
                    .color(color)
                    .companyImage(companyImage)
                    .startYear(startYear)
                    .startMonth(startMonth)
                    .build();
        }
    }

    public static MonthlyListResponseDto of(String deadline, List<ScrapDetail> scraps){
        return MonthlyListResponseDto.builder()
                .deadline(deadline)
                .scraps(scraps)
                .build();
    }
}
