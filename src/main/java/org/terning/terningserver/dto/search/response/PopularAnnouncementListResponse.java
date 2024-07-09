package org.terning.terningserver.dto.search.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.terning.terningserver.domain.InternshipAnnouncement;

import java.util.List;
import java.util.stream.Collectors;

public record PopularAnnouncementListResponse(
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

    public static PopularAnnouncementListResponse of(List<InternshipAnnouncement> announcements) {
       return new PopularAnnouncementListResponse(
               announcements.stream().map(MostViewedAndScrappedAnnouncement::from).toList()
       );
    }
}
