package org.terning.terningserver.dto.search.response;

import lombok.Builder;
import org.terning.terningserver.domain.Company;
import org.terning.terningserver.domain.InternshipAnnouncement;

import java.util.List;

public record SearchResponse(
        List<MostViewedAnnouncementResponse> announcements
) {

    @Builder
    public record MostViewedAnnouncementResponse(
            Long internshipAnnouncementId,
            String companyImage,
            String title
    ) {
        public static MostViewedAnnouncementResponse of (InternshipAnnouncement internshipAnnouncement) {
            return MostViewedAnnouncementResponse.builder()
                    .internshipAnnouncementId(internshipAnnouncement.getId())
                    .companyImage(internshipAnnouncement.getCompany().getCompanyImage())
                    .build();
        }
    }
}
