package org.terning.terningserver.home.dto.response;

import lombok.Builder;
import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;
import org.terning.terningserver.common.util.DateUtil;

@Builder
public record HomeResponseDto(
        Long internshipAnnouncementId,
        String companyImage,
        String dDay,
        String title,
        String workingPeriod,
        boolean isScrapped,
        String color,
        String deadline,
        String startYearMonth
) {
    public static HomeResponseDto of(final InternshipAnnouncement internshipAnnouncement, final boolean isScrapped, final String color){
        String dDay = DateUtil.convert(internshipAnnouncement.getDeadline()); // dDay 계산 로직 추가
        String startYearMonth = internshipAnnouncement.getStartYear() + "년 " + internshipAnnouncement.getStartMonth() + "월";
        String deadline = DateUtil.convertDeadline(internshipAnnouncement.getDeadline());

        return HomeResponseDto.builder()
                .internshipAnnouncementId(internshipAnnouncement.getId())
                .companyImage(internshipAnnouncement.getCompany().getCompanyImage())
                .dDay(dDay)
                .title(internshipAnnouncement.getTitle())
                .workingPeriod(internshipAnnouncement.getWorkingPeriod())
                .isScrapped(isScrapped)
                .color(color)
                .deadline(deadline)
                .startYearMonth(startYearMonth)
                .build();
    }
}
