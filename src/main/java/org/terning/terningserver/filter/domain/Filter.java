package org.terning.terningserver.filter.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.terning.terningserver.auth.dto.request.SignUpFilterRequest;

import static lombok.AccessLevel.PROTECTED;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Filter {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private WorkingPeriod workingPeriod;

    private int startYear;

    private int startMonth;

    public static Filter from(SignUpFilterRequest request) {
        return Filter.builder()
                .jobType(JobType.TOTAL)
                .grade(Grade.fromKey(request.grade()))
                .workingPeriod(WorkingPeriod.fromKey(request.workingPeriod()))
                .startYear(request.startYear())
                .startMonth(request.startMonth())
                .build();
    }

    public void updateFilter(JobType jobType, Grade grade, WorkingPeriod workingPeriod, int startYear, int startMonth) {
        this.jobType = jobType;
        this.grade = grade;
        this.workingPeriod = workingPeriod;
        this.startYear = startYear;
        this.startMonth = startMonth;
    }

}
