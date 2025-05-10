package org.terning.terningserver.filter.application;

import org.terning.terningserver.filter.dto.request.UpdateUserFilterRequestDto;
import org.terning.terningserver.filter.dto.response.UserFilterDetailResponseDto;

public interface FilterService {

    UserFilterDetailResponseDto getUserFilter(Long userId);

    void updateUserFilter(UpdateUserFilterRequestDto responseDto, Long userId);
}
