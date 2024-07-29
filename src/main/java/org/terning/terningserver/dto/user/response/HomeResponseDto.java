package org.terning.terningserver.dto.user.response;

import lombok.Builder;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.util.DateUtil;

@Builder
public record HomeResponseDto(
        Long scrapId,
        Long intershipAnnouncementId,
        String title,
        String dDay,
        String deadline,
        String workingPeriod,
        String startYearMonth,
        String companyImage,
        boolean isScrapped
) {
    public static HomeResponseDto of(final Long scrapId, final InternshipAnnouncement internshipAnnouncement, final boolean isScrapped){
        String dDay = DateUtil.convert(internshipAnnouncement.getDeadline()); // dDay 계산 로직 추가
        String startYearMonth = internshipAnnouncement.getStartYear() + "년 " + internshipAnnouncement.getStartMonth() + "월";
        String deadline = DateUtil.convertDeadline(internshipAnnouncement.getDeadline());

        return HomeResponseDto.builder()
                .scrapId(scrapId)
                .intershipAnnouncementId(internshipAnnouncement.getId())
                .title(internshipAnnouncement.getTitle())
                .dDay(dDay)
                .deadline(deadline)
                .workingPeriod(internshipAnnouncement.getWorkingPeriod())
                .startYearMonth(startYearMonth)
                .companyImage(internshipAnnouncement.getCompany().getCompanyImage())
                .isScrapped(isScrapped)
                .build();
    }

}
