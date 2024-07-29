package org.terning.terningserver.dto.filter.response;

import lombok.Builder;
import org.terning.terningserver.domain.Filter;

@Builder
public record UserFilterResponseDto(
        Integer grade,
        Integer workingPeriod,
        Integer startYear,
        Integer startMonth
) {
    public static UserFilterResponseDto of(Filter userFilter) {
        return UserFilterResponseDto.builder()
                .grade(userFilter == null ? null : userFilter.getGrade().getKey())
                .workingPeriod(userFilter == null ? null : userFilter.getWorkingPeriod().getKey())
                .startYear(userFilter == null ? null : userFilter.getStartYear())
                .startMonth(userFilter == null ? null : userFilter.getStartMonth())
                .build();
    }
}
