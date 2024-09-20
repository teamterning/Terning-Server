package org.terning.terningserver.service;

import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.dto.scrap.request.UpdateScrapRequestDto;
import org.terning.terningserver.dto.calendar.response.DailyScrapResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyListResponseDto;
import org.terning.terningserver.dto.user.response.UpcomingScrapResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScrapService {

    boolean hasUserScrapped(long userId);
    List<UpcomingScrapResponseDto.ScrapDetail> getUpcomingScrap(long userId);

    void createScrap(Long internshipAnnouncementId, CreateScrapRequestDto request, Long userId);

    void deleteScrap(Long scrapId, Long userId);

    void updateScrapColor(Long scrapId, UpdateScrapRequestDto request, Long userId);

    List<MonthlyDefaultResponseDto> getMonthlyScraps(Long userId, int year, int month);

    List<MonthlyListResponseDto> getMonthlyScrapsAsList(Long userId, int year, int month);

    List<DailyScrapResponseDto> getDailyScraps(Long userId, LocalDate date);
}
