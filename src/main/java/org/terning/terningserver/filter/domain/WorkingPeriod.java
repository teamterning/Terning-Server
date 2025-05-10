package org.terning.terningserver.filter.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkingPeriod {
    OPTION1("short", "1개월 ~ 3개월"),
    OPTION2("middle", "4개월 ~ 6개월"),
    OPTION3("long", "7개월 이상");

    private final String key;
    private final String value;

    public static WorkingPeriod fromKey(String key){
        for(WorkingPeriod period : WorkingPeriod.values()){
            if(period.key.equals(key)){
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
