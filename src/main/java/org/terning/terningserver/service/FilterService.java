package org.terning.terningserver.service;

import org.terning.terningserver.dto.filter.request.UpdateUserFilterRequestDto;
import org.terning.terningserver.dto.filter.response.UserFilterResponseDto;

public interface FilterService {

    UserFilterResponseDto getUserFilter(Long userId);

    void updateUserFilter(UpdateUserFilterRequestDto responseDto, Long userId);
}
