package org.terning.terningserver.internshipAnnouncement.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Company {

    @Column(nullable = false, length = 64)
    private String companyInfo;  // 기업명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyCategory companyCategory;  // 회사 카테고리

    @Column(nullable = false, length = 256)
    private String companyImage;

}
