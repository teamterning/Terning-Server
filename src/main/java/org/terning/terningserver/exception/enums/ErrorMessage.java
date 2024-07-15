package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    //404(NotFound)
    NOT_FOUND_INTERN_CATEGORY(404, "해당 인턴 공고는 존재하지 않습니다"),
    NOT_FOUND_INTERN_EXCEPTION(404, "해당 인턴 공고는 존재하지 않습니다"),
    NOT_FOUND_USER_EXCEPTION(404, "해당 유저가 존재하지 않습니다");

    private final int status;
    private final String message;
}
