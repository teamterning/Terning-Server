package org.terning.terningserver.exception.enums;



import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    INVALID_KEY(401, "유효하지 않은 키입니다."),

    INVALID_USER(404, "유효하지 않은 유저입니다.");

    private final int status;
    private final String message;
}
