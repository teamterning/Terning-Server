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
            String companyImage,
            String title,
            String workingPeriod
    ) {
        public static SearchAnnouncementResponse from(InternshipAnnouncement announcement, Long scrapId) {
            return SearchAnnouncementResponse.builder()
                    .internshipAnnouncementId(announcement.getId())
                    .scrapId(scrapId)
                    .dDay(DateUtil.convert(announcement.getDeadline()))
                    .companyImage(announcement.getCompany().getCompanyImage())
                    .title(announcement.getTitle())
                    .workingPeriod(announcement.getWorkingPeriod())
//                    .isScrapped(isScrapped)
                    .build();
        }
    }
    public static SearchResultResponseDto of(int totalPages, Boolean hasNext, List<SearchAnnouncementResponse> announcements) {
        return new SearchResultResponseDto(totalPages, hasNext, announcements);
    }

}
