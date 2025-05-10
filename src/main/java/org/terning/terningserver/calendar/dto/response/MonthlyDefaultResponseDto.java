package org.terning.terningserver.calendar.dto.response;


import lombok.Builder;

import java.util.List;

@Builder
public record MonthlyDefaultResponseDto(
        String deadline,
        List<ScrapDetail> scraps
) {
    @Builder
    public static record ScrapDetail(
            Long scrapId,
            String title,
            String color
    ){
        public static ScrapDetail of(final Long scrapId, final String title, final String color){
            return ScrapDetail.builder()
                    .scrapId(scrapId)
                    .title(title)
                    .color(color)
                    .build();
        }
    }

    public static MonthlyDefaultResponseDto of(String deadline, List<ScrapDetail> scraps){
        return MonthlyDefaultResponseDto.builder()
                .deadline(deadline)
                .scraps(scraps)
                .build();
    }
}
