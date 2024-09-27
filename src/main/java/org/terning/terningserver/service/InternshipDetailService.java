package org.terning.terningserver.service;

import org.terning.terningserver.dto.internship_detail.InternshipDetailResponseDto;


public interface InternshipDetailService {

    InternshipDetailResponseDto getInternshipDetail(long internshipAnnouncementId, long userId);
}
