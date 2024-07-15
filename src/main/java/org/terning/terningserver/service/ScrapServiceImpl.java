package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.terning.terningserver.exception.enums.ErrorMessage.NOT_FOUND_INTERN_EXCEPTION;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final ScrapRepository scrapRepository;
    private final InternshipRepository internshipRepository;

    @Override
    public List<TodayScrapResponseDto> getTodayScrap(Long userId){
        LocalDate today = LocalDate.now();
        return scrapRepository.findByUserIdAndInternshipAnnouncement_Deadline(userId, today).stream()
                .map(TodayScrapResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public void createScrap(Long internshipAnnouncementId, CreateScrapRequestDto request) {
        getInternshipAnnouncement(internshipAnnouncementId);
        Scrap.
    }

    private InternshipAnnouncement getInternshipAnnouncement(Long internshipAnnouncementId) {
        return internshipRepository.findById(internshipAnnouncementId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_INTERN_EXCEPTION));
    }
}
