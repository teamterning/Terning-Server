package org.terning.terningserver.filter.dto.request;

public record UpdateUserFilterRequestDto(
        String jobType,
        String grade,
        String workingPeriod,
        int startYear,
        int startMonth
) {
}
