package org.terning.terningserver.domain.enums;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Grade {
    FRESHMAN(1, "1학년"),
    SOPHOMORE(2, "2학년"),
    JUNIOR(3, "3학년"),
    SENIOR(4, "4학년");

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
