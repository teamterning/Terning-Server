package org.terning.terningserver.dto.search.response;

import lombok.Builder;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.util.DateUtil;

import java.util.List;

public record SearchResultResponseDto(
        int totalPages,
        Boolean hasNext,
        List<SearchAnnouncementResponse> announcements
) {
    @Builder
    public record SearchAnnouncementResponse(
            Long internshipAnnouncementId,
            Long scrapId,
            String dDay,
            String deadline,
            String companyImage,
            String title,
            String workingPeriod,
            String startYearMonth,
            String color

    ) {
        public static SearchAnnouncementResponse from(final InternshipAnnouncement announcement, final Long scrapId, final String color) {
            String startYearMonth = announcement.getStartYear() + "년 " + announcement.getStartMonth() + "월";
            String deadline = DateUtil.convertDeadline(announcement.getDeadline());

            return SearchAnnouncementResponse.builder()
                    .internshipAnnouncementId(announcement.getId())
                    .scrapId(scrapId)
                    .dDay(DateUtil.convert(announcement.getDeadline()))
                    .deadline(deadline)
                    .companyImage(announcement.getCompany().getCompanyImage())
                    .title(announcement.getTitle())
                    .workingPeriod(announcement.getWorkingPeriod())
                    .startYearMonth(startYearMonth)
                    .color(color)
//                    .isScrapped(isScrapped)
                    .build();
        }
    }
    public static SearchResultResponseDto of(int totalPages, Boolean hasNext, List<SearchAnnouncementResponse> announcements) {
        return new SearchResultResponseDto(totalPages, hasNext, announcements);
    }

}
