package org.terning.terningserver.service;

import org.terning.terningserver.dto.user.response.HomeResponseDto;

import java.util.List;

public interface HomeService {

    List<HomeResponseDto> getAnnouncements(String token, String sortBy, int startYear, int startMonth);
}
