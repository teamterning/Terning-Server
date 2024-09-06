package org.terning.terningserver.dto.filter.request;

public record UpdateUserFilterRequestDto(
        String grade,
        String workingPeriod,
        int startYear,
        int startMonth
) {
}
