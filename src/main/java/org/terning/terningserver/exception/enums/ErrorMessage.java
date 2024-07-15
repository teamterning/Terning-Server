package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    // 소셜 로그인
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    INVALID_KEY(401, "유효하지 않은 키입니다."),
    SOCIAL_LOGIN_FAILED(404, "소셜 로그인애 실패하였습니다"),
    INVALID_USER(404, "유효하지 않은 유저입니다."),
    TOKEN_REISSUE_FAILED(404, "토큰 재발급에 실패하였습니다."),

    // 회원가입
    SIGN_UP_FAILED(401, "회원가입에 실패하였습니다"),
    USER_ALREADY_EXISTS(401, "이미 존재하는 유저입니다"),

    // 사용자 필터링 정보 생성
    SIGN_UP_FILTER_FAILED(404, "회원가입 필터링 정보 생성에 실패하였습니다"),

    // 로그 아웃
    SIGN_OUT_FAILED(404, "로그아웃에 실패하였습니다"),
    REFRESH_TOKEN_RESET_FAILED(400, "리프레쉬 토큰 초기화에 실패하였습니다"),

    // 계정 탈퇴
    WITHDRAW_FAILED(404, "계정 탈퇴에 실패하였습니다"),
    
    //404(NotFound)
    NOT_FOUND_INTERN_CATEGORY(404, "해당 인턴 공고는 존재하지 않습니다"),
    NOT_FOUND_INTERN_EXCEPTION(404, "해당 인턴 공고는 존재하지 않습니다"),
    NOT_FOUND_USER_EXCEPTION(404, "해당 유저가 존재하지 않습니다");

    private final int status;
    private final String message;
}
