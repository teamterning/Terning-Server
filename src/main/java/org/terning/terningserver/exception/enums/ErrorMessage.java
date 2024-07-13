package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    INTERNSHIP_NOT_FOUND(404, "해당 id에 해당하는 인턴 공고가 존재하지 않습니다");

    private final int status;
    private final String message;
}
