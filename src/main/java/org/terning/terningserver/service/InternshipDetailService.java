package org.terning.terningserver.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.dto.internship_detail.InternshipDetailResponse;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.exception.enums.ErrorMessage;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.INTERNSHIP_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternshipDetailService {

    private final InternshipRepository internshipRepository;
    private final ScrapRepository scrapRepository;

    public InternshipDetailResponse getInternshipDetail(Long internshipAnnouncementId) {
        InternshipAnnouncement announcement = internshipRepository.findById(internshipAnnouncementId)
                .orElseThrow(() -> new CustomException(INTERNSHIP_NOT_FOUND));


        return InternshipDetailResponse.of(
                announcement, announcement.getCompany(),
                scrapRepository.existsByInternshipAnnouncementIdAndUserId(announcement.getId(), 1L)
        );
    }
}
