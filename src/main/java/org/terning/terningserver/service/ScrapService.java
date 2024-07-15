package org.terning.terningserver.service;

import org.terning.terningserver.dto.calendar.response.DailyScrapResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyListResponseDto;
import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScrapService {
    List<TodayScrapResponseDto> getTodayScrap(Long userId);
    List<MonthlyDefaultResponseDto> getMonthlyScraps(Long userId, int year, int month);
    List<MonthlyListResponseDto> getMonthlyScrapsAsList(Long userId, int year, int month);
    List<DailyScrapResponseDto> getDailyScraps(Long userId, LocalDate date);
}
