package org.terning.terningserver.filter.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.common.exception.enums.ErrorMessage;

@Getter
@RequiredArgsConstructor
public enum JobType {
    TOTAL("total", "전체"),
    PLAN("plan", "기획/전략"),
    MARKETING("marketing", "마케팅/홍보"),
    ADMIN("admin", "사무/회계"),
    SALES("sales", "인사/영업"),
    DESIGN("design", "디자인/예술"),
    IT("it", "개발/IT"),
    RESEARCH("research", "연구/생산"),
    ETC("etc", "기타");

    private final String key;
    private final String value;

    public static JobType fromKey(String key) {
        for (JobType jobType : JobType.values()) {
            if (jobType.key.equalsIgnoreCase(key)) {
                return jobType;
            }
        }
        throw new CustomException(ErrorMessage.INVALID_JOB_TYPE);
    }

}
