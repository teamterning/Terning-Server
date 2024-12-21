package org.terning.terningserver.service;

import org.springframework.data.domain.Pageable;
import org.terning.terningserver.dto.user.response.HomeAnnouncementsResponseDto;

public interface HomeService {

    HomeAnnouncementsResponseDto getAnnouncements(Long userId, String sortBy, Pageable pageable);
}
