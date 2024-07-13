package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    // 홈 화면
    SUCCESS_GET_ANNOUNCEMENTS(200, "인턴 공고 불러오기를 성공했습니다"),

    ;

    private final int status;
    private final String message;

}
