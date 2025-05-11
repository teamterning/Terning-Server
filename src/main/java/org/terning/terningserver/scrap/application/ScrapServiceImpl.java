package org.terning.terningserver.scrap.application;

import static org.terning.terningserver.common.exception.enums.ErrorMessage.EXISTS_SCRAP_ALREADY;
import static org.terning.terningserver.common.exception.enums.ErrorMessage.FORBIDDEN_DELETE_SCRAP;
import static org.terning.terningserver.common.exception.enums.ErrorMessage.NOT_FOUND_INTERN_EXCEPTION;
import static org.terning.terningserver.common.exception.enums.ErrorMessage.NOT_FOUND_SCRAP;
import static org.terning.terningserver.common.exception.enums.ErrorMessage.NOT_FOUND_USER_EXCEPTION;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;
import org.terning.terningserver.scrap.domain.Scrap;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.scrap.domain.Color;
import org.terning.terningserver.scrap.dto.request.CreateScrapRequestDto;
import org.terning.terningserver.scrap.dto.request.UpdateScrapRequestDto;
import org.terning.terningserver.calendar.dto.response.DailyScrapResponseDto;
import org.terning.terningserver.calendar.dto.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.calendar.dto.response.MonthlyListResponseDto;
import org.terning.terningserver.home.dto.response.UpcomingScrapResponseDto;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.internshipAnnouncement.repository.InternshipRepository;
import org.terning.terningserver.scrap.repository.ScrapRepository;
import org.terning.terningserver.user.repository.UserRepository;
import org.terning.terningserver.common.util.DateUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScrapServiceImpl implements ScrapService {

    private final ScrapRepository scrapRepository;
    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;

    @Override
    public boolean hasUserScrapped(long userId) {
        return scrapRepository.existsByUserId(userId);
    }

    @Override
    public List<UpcomingScrapResponseDto.ScrapDetail> getUpcomingScrap(long userId){
        LocalDate today = LocalDate.now();
        LocalDate oneWeekFromToday = today.plusDays(7);
        return scrapRepository.findScrapsByUserIdAndDeadlineBetweenOrderByDeadline(userId, today, oneWeekFromToday).stream()
                .map(UpcomingScrapResponseDto.ScrapDetail::of)
                .toList();
    }

    @Override
    public List<MonthlyDefaultResponseDto> getMonthlyScraps(Long userId, int year, int month){

        //모든 월의 시작일은 1, 마지막일은 해당 다음월의 하루 전
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        List<Scrap> scraps = scrapRepository.findScrapsByUserIdAndDeadlineBetweenOrderByDeadline(userId, start, end);

        //deadline 별로 그룹화
        Map<LocalDate, List<Scrap>> scrapsByDeadline = scraps.stream()
                .collect(Collectors.groupingBy(s -> s.getInternshipAnnouncement().getDeadline()));

        return scrapsByDeadline.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
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

        List<Scrap> scraps = scrapRepository.findScrapsByUserIdAndDeadlineBetweenOrderByDeadline(userId, start, end);

        //deadline 별로 그룹화
        Map<LocalDate, List<Scrap>> scrapsByDeadline = scraps.stream()
                .collect(Collectors.groupingBy(s -> s.getInternshipAnnouncement().getDeadline()));

        return scrapsByDeadline.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> MonthlyListResponseDto.of(
                        entry.getKey().toString(),
                        entry.getValue().stream()
                                .map(s -> MonthlyListResponseDto.ScrapDetail.of(
                                        s.getInternshipAnnouncement().getId(),
                                        s.getInternshipAnnouncement().getCompany().getCompanyImage(),
                                        DateUtil.convert(s.getInternshipAnnouncement().getDeadline()),
                                        s.getInternshipAnnouncement().getTitle(),
                                        s.getInternshipAnnouncement().getWorkingPeriod(),
                                        true, // 이미 스크랩된 경우이므로 true
                                        s.getColorToHexValue(),
                                        DateUtil.convertDeadline(s.getInternshipAnnouncement().getDeadline()),
                                        s.getInternshipAnnouncement().getStartYear() + "년 " + s.getInternshipAnnouncement().getStartMonth() + "월"
                                ))
                                .toList()
                ))
                .toList();
    }

    @Override
    public List<DailyScrapResponseDto> getDailyScraps(Long userId, LocalDate date) {
        return scrapRepository.findScrapsByUserIdAndDeadlineOrderByDeadline(userId, date).stream()
                .map(DailyScrapResponseDto::of)
                .toList();
    }

    @Override
    @Transactional
    public void createScrap(Long internshipAnnouncementId, CreateScrapRequestDto request, Long userId) {

        if(scrapRepository.existsByInternshipAnnouncementIdAndUserId(internshipAnnouncementId, userId)) {
            throw new CustomException(EXISTS_SCRAP_ALREADY);
        }

        InternshipAnnouncement announcement = getInternshipAnnouncement(internshipAnnouncementId);
        scrapRepository.save(Scrap.create(
                findUser(userId),
                announcement,
                findColor(request.color())
        ));
        updateScrapCount(announcement, 1);
    }

    @Override
    @Transactional
    public void deleteScrap(Long internshipAnnouncementId, Long userId) {
        Scrap scrap = findScrap(internshipAnnouncementId, userId);
        InternshipAnnouncement announcement = getInternshipAnnouncement(internshipAnnouncementId);
        verifyScrapOwner(scrap, userId);
        scrapRepository.deleteByInternshipAnnouncementIdAndUserId(internshipAnnouncementId, userId);
        updateScrapCount(announcement, -1);
    }

    @Override
    @Transactional
    public void updateScrapColor(Long internshipAnnouncementId, UpdateScrapRequestDto request, Long userId) {
        Scrap scrap = findScrap(internshipAnnouncementId, userId);
        verifyScrapOwner(scrap, userId);
        scrap.updateColor(Color.findByName(request.color()));
    }

    private void updateScrapCount(InternshipAnnouncement announcement, int plusOrMinus) {
        announcement.updateScrapCount(plusOrMinus);
    }

    private void verifyScrapOwner(Scrap scrap, Long userId) {
        if(!scrap.getUser().equals(findUser(userId))) {
            throw new CustomException(FORBIDDEN_DELETE_SCRAP);
        }
    }

    private Color findColor(String color) {
        return Arrays.stream(Color.values())
                .filter(c-> c.getName().equals(color))
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

    private Scrap findScrap(Long internshipAnnouncementId, Long userId) {
        return scrapRepository.findByInternshipAnnouncementIdAndUserId(internshipAnnouncementId, userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SCRAP));
    }
}

