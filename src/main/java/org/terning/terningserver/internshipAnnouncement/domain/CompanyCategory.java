package org.terning.terningserver.internshipAnnouncement.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CompanyCategory {

    LARGE_AND_MEDIUM_COMPANIES(0, "대기업/중견기업"),
    SMALL_COMPANIES(1, "중소기업"),
    PUBLIC_INSTITUTIONS(2, "공공기관/공기업"),
    FOREIGN_COMPANIES(3, "외국계기업"),
    STARTUPS(4, "스타트업"),
    NON_PROFIT_ORGANIZATIONS(5, "비영리단체/재단"),
    OTHERS(6, "기타");

    private final int key;
    private final String value;

    public static CompanyCategory fromKey(int key){
        for(CompanyCategory category : CompanyCategory.values()){
            if(category.key == key){
                return category;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 요청 값입니다.");
    }
    public static CompanyCategory fromString(String value){
        for(CompanyCategory category : CompanyCategory.values()){
            if(category.value.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 요청 값입니다.");
    }
}
