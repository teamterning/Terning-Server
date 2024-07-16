package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.dto.calendar.response.DailyScrapResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyListResponseDto;
import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;
import org.terning.terningserver.repository.scrap.ScrapRepository;
import org.terning.terningserver.util.DateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final ScrapRepository scrapRepository;

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
    public List<DailyScrapResponseDto> getDailyScraps(Long userId, LocalDate date){
        return scrapRepository.findByUserIdAndInternshipAnnouncement_Deadline(userId, date).stream()
                .map(DailyScrapResponseDto::of)
                .toList();
    }
}
