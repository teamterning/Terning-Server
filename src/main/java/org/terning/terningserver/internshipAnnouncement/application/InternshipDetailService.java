package org.terning.terningserver.internshipAnnouncement.application;

import org.terning.terningserver.internshipAnnouncement.dto.response.InternshipDetailResponseDto;


public interface InternshipDetailService {

    InternshipDetailResponseDto getInternshipDetail(long internshipAnnouncementId, long userId);
}
