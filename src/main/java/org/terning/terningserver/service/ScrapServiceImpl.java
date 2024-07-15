package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.dto.user.response.TodayScrapResponseDto;
import org.terning.terningserver.repository.scrap.ScrapRepository;

import java.time.LocalDate;
import java.util.List;
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
                .collect(Collectors.toList());
    }
}
