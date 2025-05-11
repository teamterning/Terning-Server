package org.terning.terningserver.home.application;

import org.springframework.data.domain.Pageable;
import org.terning.terningserver.home.dto.response.HomeAnnouncementsResponseDto;

public interface HomeService {

    HomeAnnouncementsResponseDto getAnnouncements(Long userId, String sortBy, Pageable pageable);
}
