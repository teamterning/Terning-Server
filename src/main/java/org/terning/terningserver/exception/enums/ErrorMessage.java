package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    //404(NotFound)
    NOT_FOUND_INTERN_CATEGORY(404, "해당 인턴 공고는 존재하지 않습니다"),
    NOT_FOUND_INTERN_EXCEPTION(404, "해당 인턴 공고는 존재하지 않습니다"),
    WRONG_PERIOD(404, "해당 단어가 존재하지 않습니다"),
    NOT_FOUND_USER_EXCEPTION(404, "해당 유저가 존재하지 않습니다"),
    NOT_FOUND_SCRAP(404, "해당 스크랩 id에 대한 스크랩 정보가 존재하지 않습니다"),
    FORBIDDEN_DELETE_SCRAP(403, "해당 유저가 스크랩하지 않았으므로 스크랩 취소가 불가합니다");

    private final int status;
    private final String message;
}
