package org.terning.terningserver.dto.auth.request;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignUpFilterRequestDto(
        @NonNull int grade,
        @NonNull int workingPeriod,
        @NonNull int startYear,
        @NonNull int startMonth

) {
    public static SignUpFilterRequestDto of(int grade, int workingPeriod, int startYear, int startMonth) {
        return SignUpFilterRequestDto.builder()
                .grade(grade)
                .workingPeriod(workingPeriod)
                .startYear(startYear)
                .startMonth(startMonth)
                .build();
    }
}
