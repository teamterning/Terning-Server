package org.terning.terningserver.dto.filter.response;

import lombok.Builder;
import org.terning.terningserver.domain.Filter;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record UserFilterDetailResponseDto(
        String grade,
        String workingPeriod,
        Integer startYear,
        Integer startMonth
) {
    public static UserFilterDetailResponseDto of(final Filter userFilter) {
        return UserFilterDetailResponseDto.builder()
                .grade(userFilter == null ? null : userFilter.getGrade().getKey())
                .workingPeriod(userFilter == null ? null : userFilter.getWorkingPeriod().getKey())
                .startYear(userFilter == null ? null : userFilter.getStartYear())
                .startMonth(userFilter == null ? null : userFilter.getStartMonth())
                .build();
    }
}
