package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.Color;
import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.dto.scrap.request.UpdateScrapRequestDto;
import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;
import org.terning.terningserver.repository.user.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.terning.terningserver.exception.enums.ErrorMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScrapServiceImpl implements ScrapService {

    private final ScrapRepository scrapRepository;
    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;

    @Override
    public List<TodayScrapResponseDto> getTodayScrap(Long userId){
        LocalDate today = LocalDate.now();
        return scrapRepository.findByUserIdAndInternshipAnnouncement_Deadline(userId, today).stream()
                .map(TodayScrapResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createScrap(Long internshipAnnouncementId, CreateScrapRequestDto request) {
        getInternshipAnnouncement(internshipAnnouncementId);

        scrapRepository.save(Scrap.create(
                findUser(1L),
                getInternshipAnnouncement(internshipAnnouncementId),
                findColor(request.color())
        ));
    }

    @Override
    @Transactional
    public void deleteScrap(Long scrapId) {
        Scrap scrap = findScrap(scrapId);
        verifyScrapOwner(scrap);
        scrapRepository.deleteById(scrapId);
    }

    @Override
    @Transactional
    public void updateScrapColor(Long scrapId, UpdateScrapRequestDto request) {
        Scrap scrap = findScrap(scrapId);
        verifyScrapOwner(scrap);
        scrap.updateColor(findColor(request.color()));
    }

    //토큰으로 찾은(요청한) User와 스크랩한 User가 일치한지 여부 검증하는 메서드
    private void verifyScrapOwner(Scrap scrap) {
        if(!scrap.getUser().equals(findUser(1L))) {
            throw new CustomException(FORBIDDEN_DELETE_SCRAP);
        }
    }

    private Color findColor(int color) {
        return Arrays.stream(Color.values())
                .filter(c-> c.getKey() == color)
                .findAny().get();
    }

    private InternshipAnnouncement getInternshipAnnouncement(Long internshipAnnouncementId) {
        return internshipRepository.findById(internshipAnnouncementId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_INTERN_EXCEPTION));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_EXCEPTION));
    }

    private Scrap findScrap(Long scrapId) {
        return scrapRepository.findById(scrapId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SCRAP));
    }
}
