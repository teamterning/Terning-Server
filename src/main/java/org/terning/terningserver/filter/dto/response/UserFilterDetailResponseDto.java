package org.terning.terningserver.filter.dto.response;

import lombok.Builder;
import org.terning.terningserver.filter.domain.Filter;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record UserFilterDetailResponseDto(
        String jobType,
        String grade,
        String workingPeriod,
        Integer startYear,
        Integer startMonth
) {
    public static UserFilterDetailResponseDto of(final Filter userFilter) {
        return UserFilterDetailResponseDto.builder()
                .jobType(userFilter == null || userFilter.getJobType() == null ? "total" : userFilter.getJobType().getKey())
                .grade(userFilter == null || userFilter.getGrade() == null ? null : userFilter.getGrade().getKey())
                .workingPeriod(userFilter == null || userFilter.getWorkingPeriod() == null ? null : userFilter.getWorkingPeriod().getKey())
                .startYear(userFilter == null ? null : userFilter.getStartYear())
                .startMonth(userFilter == null ? null : userFilter.getStartMonth())
                .build();
    }
}
