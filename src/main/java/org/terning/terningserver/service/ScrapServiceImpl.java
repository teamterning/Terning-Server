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
import org.terning.terningserver.dto.calendar.response.DailyScrapResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyListResponseDto;
import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.jwt.PrincipalHandler;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;
import org.terning.terningserver.repository.user.UserRepository;
import org.terning.terningserver.util.DateUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
                .toList();
    }

    @Override
    public List<MonthlyDefaultResponseDto> getMonthlyScraps(Long userId, int year, int month){

        //모든 월의 시작일은 1, 마지막일은 해당 다음월의 하루 전
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        List<Scrap> scraps = scrapRepository.findByUserIdAndInternshipAnnouncement_DeadlineBetween(userId, start, end);

        //deadline 별로 그룹화
        Map<LocalDate, List<Scrap>> scrapsByDeadline = scraps.stream()
                .collect(Collectors.groupingBy(s -> s.getInternshipAnnouncement().getDeadline()));

        return scrapsByDeadline.entrySet().stream()
                .map(entry -> MonthlyDefaultResponseDto.of(
                        entry.getKey().toString(),
                        entry.getValue().stream()
                                .map(s -> MonthlyDefaultResponseDto.ScrapDetail.of(
                                        s.getId(),
                                        s.getInternshipAnnouncement().getTitle(),
                                        s.getColor().getColorValue()
                                ))
                                .toList()
                ))
                .toList();
    }

    @Override
    public List<MonthlyListResponseDto> getMonthlyScrapsAsList(Long userId, int year, int month){

        //모든 월의 시작일은 1, 마지막일은 해당 다음월의 하루 전
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        List<Scrap> scraps = scrapRepository.findByUserIdAndInternshipAnnouncement_DeadlineBetween(userId, start, end);

        //deadline 별로 그룹화
        Map<LocalDate, List<Scrap>> scrapsByDeadline = scraps.stream()
                .collect(Collectors.groupingBy(s -> s.getInternshipAnnouncement().getDeadline()));

        return scrapsByDeadline.entrySet().stream()
                .map(entry -> MonthlyListResponseDto.of(
                        entry.getKey().toString(),
                        entry.getValue().stream()
                                .map(s -> MonthlyListResponseDto.ScrapDetail.of(
                                        s.getId(),
                                        s.getInternshipAnnouncement().getId(),
                                        s.getInternshipAnnouncement().getTitle(),
                                        DateUtil.convert(s.getInternshipAnnouncement().getDeadline()),
                                        s.getInternshipAnnouncement().getWorkingPeriod(),
                                        s.getColor().getColorValue(),
                                        s.getInternshipAnnouncement().getCompany().getCompanyImage(),
                                        s.getInternshipAnnouncement().getStartYear(),
                                        s.getInternshipAnnouncement().getStartMonth()
                                ))
                                .toList()
                ))
                .toList();
    }
  
    @Override
    public List<DailyScrapResponseDto> getDailyScraps(Long userId, LocalDate date) {
        return scrapRepository.findByUserIdAndInternshipAnnouncement_Deadline(userId, date).stream()
                .map(DailyScrapResponseDto::of)
                .toList();
    }

    @Override
    @Transactional
    public void createScrap(Long internshipAnnouncementId, CreateScrapRequestDto request, Long userId) {
        getInternshipAnnouncement(internshipAnnouncementId);

        scrapRepository.save(Scrap.create(
                findUser(PrincipalHandler.getUserIdFromPrincipal()),
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

