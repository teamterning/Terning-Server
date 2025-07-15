package org.terning.terningserver.auth.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {

    INVALID_USER_ID_TYPE(HttpStatus.BAD_REQUEST, "사용자 ID의 타입이 유효하지 않습니다."),
    EMPTY_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 비어있거나 유효하지 않은 형식입니다."),

    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "HTTP Authorization 헤더를 찾을 수 없습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
    SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED, "토큰 서명 검증에 실패했습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 방식의 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 처리 중 예상치 못한 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}
