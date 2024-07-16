package org.terning.terningserver.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.terning.terningserver.domain.enums.Grade;
import org.terning.terningserver.domain.enums.WorkingPeriod;
import static lombok.AccessLevel.PROTECTED;
import static jakarta.persistence.GenerationType.IDENTITY;
import java.time.YearMonth;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Filter {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkingPeriod workingPeriod;

    private int startYear; // 근무 시작 연도

    private int startMonth; // 근무 시작 월

    public void updateFilter(Grade grade, WorkingPeriod workingPeriod, int startYear, int startMonth) {
        this.grade = grade;
        this.workingPeriod = workingPeriod;
        this.startYear = startYear;
        this.startMonth = startMonth;
    }
}
