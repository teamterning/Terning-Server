package org.terning.terningserver.domain.enums;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WorkingPeriod {
    OPTION1(0, "1개월 ~ 3개월"),
    OPTION2(1, "4개월 ~ 6개월"),
    OPTION3(2, "7개월 이상");

    private final int key;
    private final String value;

    public static WorkingPeriod fromKey(int key){
        for(WorkingPeriod period : WorkingPeriod.values()){
            if(period.key == key){
                return period;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 요청 값입니다.");
    }
    public static WorkingPeriod fromString(String value){
        for(WorkingPeriod period : WorkingPeriod.values()){
            if(period.value.equalsIgnoreCase(value)) {
                return period;
            }
        }
        throw new IllegalArgumentException("올바르지 않은 요청 값입니다.");
    }
}
