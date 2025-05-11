package org.terning.terningserver.auth.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    ;
    public static final String PREFIX = "[AUTH ERROR]";

    private final HttpStatus status;
    private final String rawMessage;

    public String getMessage() {
        return PREFIX + " " + rawMessage;
    }
}
