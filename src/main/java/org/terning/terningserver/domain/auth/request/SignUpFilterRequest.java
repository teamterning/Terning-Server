package org.terning.terningserver.domain.auth.request;

import lombok.Builder;
import lombok.NonNull;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
public record SignUpFilterRequest(
        @NonNull int grade,
        @NonNull int workingPeriod,
        @NonNull int startYear,
        @NonNull int startMonth

) {
}
