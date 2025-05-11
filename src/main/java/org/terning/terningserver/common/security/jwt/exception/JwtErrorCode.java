package org.terning.terningserver.common.security.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다."),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 userId 값입니다."),
    INVALID_USER_ID_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 userId 타입입니다."),
    INVALID_USER_DETAILS_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 UserDetail 타입입니다."),
    ;

    public static final String PREFIX = "[JWT ERROR]";

    private final HttpStatus status;
    private final String rawMessage;

    public String getMessage() {
        return PREFIX + " " + rawMessage;
    }
}
