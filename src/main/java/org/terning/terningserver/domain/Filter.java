package org.terning.terningserver.domain;

import jakarta.persistence.*;
import lombok.Builder;
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

    @Column(nullable = false)
    private int startYear;

    @Column(nullable = false)
    private int startMonth;


    @Builder
    public Filter(Long id, Grade grade, WorkingPeriod workingPeriod, int startYear, int startMonth) {
        this.id = id;
        this.grade = grade;
        this.workingPeriod = workingPeriod;
        this.startYear = startYear;
        this.startMonth = startMonth;
    }

}
