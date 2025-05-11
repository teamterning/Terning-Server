package org.terning.terningserver.scrap.application;

import org.terning.terningserver.scrap.dto.request.CreateScrapRequestDto;
import org.terning.terningserver.scrap.dto.request.UpdateScrapRequestDto;
import org.terning.terningserver.calendar.dto.response.DailyScrapResponseDto;
import org.terning.terningserver.calendar.dto.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.calendar.dto.response.MonthlyListResponseDto;
import org.terning.terningserver.home.dto.response.UpcomingScrapResponseDto;

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
