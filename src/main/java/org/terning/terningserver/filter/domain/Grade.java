package org.terning.terningserver.filter.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    FRESHMAN("freshman", "1학년"),
    SOPHOMORE("sophomore", "2학년"),
    JUNIOR("junior", "3학년"),
    SENIOR("senior", "4학년");

    private final String key;
    private final String value;
    
    public static Grade fromKey(String key){
        for(Grade grade : Grade.values()){
            if(grade.key.equals(key)){
                return grade;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 요청 값입니다.");
    }

    public static Grade fromString(String value){
        for(Grade grade : Grade.values()){
            if(grade.value.equalsIgnoreCase(value)) {
                return grade;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 요청 값입니다.");
    }

}
