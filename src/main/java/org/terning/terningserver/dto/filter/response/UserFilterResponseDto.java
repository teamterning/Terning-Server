package org.terning.terningserver.dto.filter.response;

import lombok.Builder;
import org.terning.terningserver.domain.Filter;

@Builder
public record UserFilterResponseDto(
        int grade,
        int workingPeriod,
        int startYear,
        int startMonth
) {
    public static UserFilterResponseDto of(Filter userFilter) {
        return UserFilterResponseDto.builder()
                .grade(userFilter.getGrade().getKey())
                .workingPeriod(userFilter.getWorkingPeriod().getKey())
                .startYear(userFilter.getStartYear())
                .startMonth(userFilter.getStartMonth())
                .build();
    }
}
