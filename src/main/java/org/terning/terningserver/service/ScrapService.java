package org.terning.terningserver.service;

import org.terning.terningserver.dto.scrap.request.CreateScrapRequestDto;
import org.terning.terningserver.dto.scrap.request.UpdateScrapRequestDto;
import org.terning.terningserver.dto.calendar.response.MonthlyDefaultResponseDto;
import org.terning.terningserver.dto.calendar.response.MonthlyListResponseDto;
import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;

import java.util.List;

public interface ScrapService {
    List<TodayScrapResponseDto> getTodayScrap(Long userId);

    void createScrap(Long internshipAnnouncementId, CreateScrapRequestDto request);

    void deleteScrap(Long scrapId);

    void updateScrapColor(Long scrapId, UpdateScrapRequestDto request);

    List<MonthlyDefaultResponseDto> getMonthlyScraps(Long userId, int year, int month);

    List<MonthlyListResponseDto> getMonthlyScrapsAsList(Long userId, int year, int month);
}
