package org.terning.terningserver.dto.user.response;

import lombok.Builder;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.util.DateUtil;

@Builder
public record HomeResponseDto(
        Long intershipAnnouncementId,
        String title,
        String dDay,
        String workingPeriod,
        String companyImage,
        boolean isScrapped
) {
    public static HomeResponseDto of(final InternshipAnnouncement internshipAnnouncement, final boolean isScrapped){
        String dDay = DateUtil.convert(internshipAnnouncement.getDeadline()); // dDay 계산 로직 추가

        return HomeResponseDto.builder()
                .intershipAnnouncementId(internshipAnnouncement.getId())
                .title(internshipAnnouncement.getTitle())
                .dDay(dDay)
                .workingPeriod(internshipAnnouncement.getWorkingPeriod())
                .companyImage(internshipAnnouncement.getCompany().getCompanyImage())
                .isScrapped(isScrapped)
                .build();
    }

}
