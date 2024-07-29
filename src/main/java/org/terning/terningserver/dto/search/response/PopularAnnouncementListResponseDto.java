package org.terning.terningserver.dto.search.response;

import lombok.Builder;
import org.terning.terningserver.domain.InternshipAnnouncement;

import java.util.List;

public record PopularAnnouncementListResponseDto(
        List<MostViewedAndScrappedAnnouncement> announcements
) {

    @Builder
    public record MostViewedAndScrappedAnnouncement(
            Long internshipAnnouncementId,
            String companyImage,
            String title
    ) {
        public static MostViewedAndScrappedAnnouncement from(InternshipAnnouncement announcement) {
            return MostViewedAndScrappedAnnouncement.builder()
                    .internshipAnnouncementId(announcement.getId())
                    .companyImage(announcement.getCompany().getCompanyImage())
                    .title(announcement.getTitle())
                    .build();
        }
    }

    public static PopularAnnouncementListResponseDto of(List<InternshipAnnouncement> announcements) {
       return new PopularAnnouncementListResponseDto(
               announcements.stream().map(MostViewedAndScrappedAnnouncement::from).toList()
       );
    }
}
