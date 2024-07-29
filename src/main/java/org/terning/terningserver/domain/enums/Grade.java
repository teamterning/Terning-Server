package org.terning.terningserver.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    FRESHMAN(0, "1학년"),
    SOPHOMORE(1, "2학년"),
    JUNIOR(2, "3학년"),
    SENIOR(3, "4학년");

    private final int key;
    private final String value;
    
    public static Grade fromKey(int key){
        for(Grade grade : Grade.values()){
            if(grade.key == key){
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
