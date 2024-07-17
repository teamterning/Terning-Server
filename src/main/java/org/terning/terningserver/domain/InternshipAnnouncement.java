package org.terning.terningserver.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.terning.terningserver.domain.common.BaseTimeEntity;
import org.terning.terningserver.domain.enums.Grade;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class InternshipAnnouncement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;  // 고유 ID

    @Column(nullable = false, length = 64)
    private String title;  // 인턴십 제목

    @Column(nullable = false)
    private LocalDate deadline;  // 지원 마감일

    @Column(length = 16)
    private String workingPeriod;  // 근무 기간

    @Column(nullable = false)
    private int startYear;  // 시작 연도

    @Column(nullable = false)
    private int startMonth;  // 시작 월

    @Column(nullable = false)
    private int viewCount;  // 조회 수

    @Column(nullable = false)
    private int scrapCount;  // 스크랩 수

    @Column(nullable = false, length = 256)
    private String url;  // 인턴십 공고 URL

    @OneToMany(mappedBy = "internshipAnnouncement")
    private List<Scrap> scraps;

    @Embedded
    private Company company;

    @Column(columnDefinition = "TEXT")
    private String qualifications;  // 자격 요건

    @Column(columnDefinition = "TEXT")
    private String jobType;  // 직무 유형

    @Column(columnDefinition = "TEXT")
    private String detail;  // 상세 내용

    @Column(nullable = false)
    private boolean isGraduating; // 졸업 예정 여부

    @OneToMany(mappedBy = "internshipAnnouncement", cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>(); //스크랩 리스트

    public void updateViewCount() {
        this.viewCount += 1;
    }
}
