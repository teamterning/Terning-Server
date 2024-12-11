package org.terning.terningserver.service;

import org.terning.terningserver.dto.user.response.HomeAnnouncementsResponseDto;

public interface HomeService {

    HomeAnnouncementsResponseDto getAnnouncements(Long userId, String sortBy);
}
