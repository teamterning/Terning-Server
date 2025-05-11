package org.terning.terningserver.search.dto.response;

import lombok.Builder;
import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;
import org.terning.terningserver.common.util.DateUtil;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

public record SearchResultResponseDto(
        int totalPages,
        long totalCount,
        Boolean hasNext,
        List<SearchAnnouncementResponse> announcements
) {
    @Builder(access = PRIVATE)
    public record SearchAnnouncementResponse(
            long internshipAnnouncementId,
            String companyImage,
            String dDay,
            String title,
            String workingPeriod,
            boolean isScrapped,
            String color,
            String deadline,
            String startYearMonth

    ) {
        public static SearchAnnouncementResponse from(
                final InternshipAnnouncement announcement,
                final Long scrapId,
                final String color
        ) {
            return SearchAnnouncementResponse.builder()
                    .internshipAnnouncementId(announcement.getId())
                    .companyImage(announcement.getCompany().getCompanyImage())
                    .dDay(DateUtil.convert(announcement.getDeadline()))
                    .title(announcement.getTitle())
                    .workingPeriod(announcement.getWorkingPeriod())
                    .isScrapped(scrapId!=null)
                    .color(color)
                    .deadline(DateUtil.convertDeadline(announcement.getDeadline()))
                    .startYearMonth(announcement.getStartYear() + "년 " + announcement.getStartMonth() + "월")
                    .build();
        }
    }
    public static SearchResultResponseDto of(
            final int totalPages,
            final long totalCount,
            final Boolean hasNext,
            final List<SearchAnnouncementResponse> announcements
    ) {
        return new SearchResultResponseDto(totalPages, totalCount, hasNext, announcements);
    }

}
