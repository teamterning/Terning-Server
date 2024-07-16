package org.terning.terningserver.dto.filter.request;

public record UserFilterRequestDto(

        Long userId,
        int grade,
        int workingPeriod,
        int startYear,
        int startMonth
) {
}
