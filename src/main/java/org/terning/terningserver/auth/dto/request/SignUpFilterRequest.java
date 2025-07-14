package org.terning.terningserver.auth.dto.request;

public record SignUpFilterRequest(
        String grade,
        String workingPeriod,
        int startYear,
        int startMonth
) {
}
