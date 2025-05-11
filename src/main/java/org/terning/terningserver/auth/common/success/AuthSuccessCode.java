package org.terning.terningserver.auth.common.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.terning.terningserver.common.exception.SuccessCode;

@Getter
@AllArgsConstructor
public enum AuthSuccessCode implements SuccessCode {
    SUCCESS_SIGN_IN(HttpStatus.OK, "로그인에 성공했습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
