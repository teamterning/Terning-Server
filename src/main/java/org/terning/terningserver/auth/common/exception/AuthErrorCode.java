package org.terning.terningserver.auth.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {

    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "유저가 이미 존재합니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    ;
    public static final String PREFIX = "[AUTH ERROR]";

    private final HttpStatus status;
    private final String rawMessage;

    public String getMessage() {
        return PREFIX + " " + rawMessage;
    }
}
