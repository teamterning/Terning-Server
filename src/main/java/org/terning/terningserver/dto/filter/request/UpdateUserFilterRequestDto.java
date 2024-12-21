package org.terning.terningserver.dto.filter.request;

public record UpdateUserFilterRequestDto(
        String jobType,
        String grade,
        String workingPeriod,
        int startYear,
        int startMonth
) {
}
