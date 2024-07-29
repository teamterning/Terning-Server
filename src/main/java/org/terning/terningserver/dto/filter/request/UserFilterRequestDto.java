package org.terning.terningserver.dto.filter.request;

public record UserFilterRequestDto(
        int grade,
        int workingPeriod,
        int startYear,
        int startMonth
) {
}
