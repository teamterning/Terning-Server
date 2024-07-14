package org.terning.terningserver.service;

import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;

import java.util.List;

public interface ScrapService {
    List<TodayScrapResponseDto> getTodayScrap(Long userId);
}
