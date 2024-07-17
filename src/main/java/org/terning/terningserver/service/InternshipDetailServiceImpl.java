package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.dto.internship_detail.InternshipDetailResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.exception.enums.ErrorMessage;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternshipDetailServiceImpl implements InternshipDetailService {
    private final InternshipRepository internshipRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public InternshipDetailResponseDto getInternshipDetail(Long internshipAnnouncementId) {
        InternshipAnnouncement announcement = internshipRepository.findById(internshipAnnouncementId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_INTERN_EXCEPTION));

        announcement.updateViewCount();

        return InternshipDetailResponseDto.of(
                announcement, announcement.getCompany(),
                scrapRepository.existsByInternshipAnnouncementIdAndUserId(announcement.getId(), 1L)
        );
    }
}
