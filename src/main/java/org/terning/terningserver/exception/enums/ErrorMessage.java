package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_INTERN_CATEGORY(404, "해당 인턴 공고는 존재하지 않습니다."),
    WRONG_PERIOD(404, "해당 단어가 존재하지 않습니다.");

    private final int status;
    private final String message;
}
