package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.dto.internship_detail.InternshipDetailResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.exception.enums.ErrorMessage;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InternshipDetailServiceImpl implements InternshipDetailService {
    private final InternshipRepository internshipRepository;
    private final ScrapRepository scrapRepository;

    @Override
    @Transactional
    public InternshipDetailResponseDto getInternshipDetail(long internshipAnnouncementId, long userId) {
        InternshipAnnouncement announcement = internshipRepository.findById(internshipAnnouncementId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_INTERN_EXCEPTION));

        announcement.updateViewCount();
        Optional<Scrap> scrap = scrapRepository.findByInternshipAnnouncementIdAndUserId(announcement.getId(), userId);

        if (scrap.isPresent()) {
            return InternshipDetailResponseDto.of(
                    announcement, announcement.getCompany(),
                    scrap.get().getId(), scrap.get().getColor().getColorValue()
            );
        } else {
            return InternshipDetailResponseDto.of(
                    announcement, announcement.getCompany(),
                    null, null
            );
        }
    }
}
