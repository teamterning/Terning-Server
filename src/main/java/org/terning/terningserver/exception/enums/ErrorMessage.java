package org.terning.terningserver.exception.enums;



import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_INTERN_CATEGORY(404, "해당 인턴 공고는 존재하지 않습니다."),
    WRONG_PERIOD(404, "해당 단어가 존재하지 않습니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    INVALID_KEY(401, "유효하지 않은 키입니다.");

    private final int status;
    private final String message;
}
