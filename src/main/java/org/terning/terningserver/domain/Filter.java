package org.terning.terningserver.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.terning.terningserver.domain.enums.Grade;
import org.terning.terningserver.domain.enums.WorkingPeriod;
import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;

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
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private WorkingPeriod workingPeriod;

    private int startYear;

    private int startMonth;

    private int startMonth; // 근무 시작 월

    public void updateFilter(Grade grade, WorkingPeriod workingPeriod, int startYear, int startMonth) {
        this.grade = grade;
        this.workingPeriod = workingPeriod;
        this.startYear = startYear;
        this.startMonth = startMonth;
    }
}
