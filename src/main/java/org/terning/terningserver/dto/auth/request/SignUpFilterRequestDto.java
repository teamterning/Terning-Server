package org.terning.terningserver.dto.auth.request;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignUpFilterRequestDto(
        String grade,
        String workingPeriod,
        int startYear,
        int startMonth

) {
    public static SignUpFilterRequestDto of(String grade, String workingPeriod, int startYear, int startMonth) {
        return SignUpFilterRequestDto.builder()
                .grade(grade)
                .workingPeriod(workingPeriod)
                .startYear(startYear)
                .startMonth(startMonth)
                .build();
    }
}
