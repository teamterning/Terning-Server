package org.terning.terningserver.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    // 소셜 로그인
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다"),
    INVALID_KEY(401, "유효하지 않은 키입니다"),
    FAILED_SOCIAL_LOGIN(404, "소셜 로그인에 실패하였습니다"),
    INVALID_USER(404, "유효하지 않은 유저입니다"),
    FAILED_TOKEN_REISSUE(404, "토큰 재발급에 실패하였습니다"),
    UNAUTHORIZED_JWT_EXCEPTION(401, "유효하지 않은 토큰입니다"),

    // 필터링
    FAILED_SIGN_UP_USER_FILTER_CREATION(404, "사용자 필터 생성에 실패하였습니다"),
    FAILED_SIGN_UP_USER_FILTER_ASSIGNMENT(404, "사용자 필터 연결에 실패하였습니다"),
    INVALID_JOB_TYPE(401, "유효하지 않은 직무 카테고리입니다."),

    // 스크랩
    EXISTS_SCRAP_ALREADY(400, "이미 스크랩했습니다."),
    INVALID_SCRAP_COLOR(401, "유효하지 않은 스크랩 색상입니다."),

    // 로그 아웃
    FAILED_SIGN_OUT(404, "로그아웃에 실패하였습니다"),
    FAILED_REFRESH_TOKEN_RESET(400, "리프레쉬 토큰 초기화에 실패하였습니다"),

    // 계정 탈퇴
    FAILED_WITHDRAW(400, "계정 탈퇴에 실패하였습니다"),

    // 마이페이지
    INVALID_PROFILE_IMAGE(404, "유효하지 않은 프로필 이미지 입니다."),
    
    // 404(NotFound)
    NOT_FOUND_INTERN_CATEGORY(404, "해당 인턴 공고는 존재하지 않습니다"),
    NOT_FOUND_INTERN_EXCEPTION(404, "해당 인턴 공고는 존재하지 않습니다"),
    NOT_FOUND_USER_EXCEPTION(404, "해당 유저가 존재하지 않습니다"),
    NOT_FOUND_SCRAP(404, "스크랩 정보가 존재하지 않습니다"),
    FORBIDDEN_DELETE_SCRAP(403, "해당 유저가 스크랩하지 않았으므로 스크랩 취소가 불가합니다"),


    INVALID_ARGUMENT_ERROR( 400, "올바르지 않은 파라미터입니다."),
    INVALID_FORMAT_ERROR(400, "올바르지 않은 포맷입니다."),
    INVALID_TYPE_ERROR(400, "올바르지 않은 타입입니다."),
    ILLEGAL_ARGUMENT_ERROR( 400, "필수 파라미터가 없습니다"),
    INVALID_HTTP_METHOD( 400, "잘못된 Http Method 요청입니다."),
    INTERNAL_SERVER_ERROR( 500, "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    ALREADY_ENABLED_PUSH_NOTIFICATION(400, "이미 푸시 알림이 활성화되어 있습니다."),
    ALREADY_DISABLED_PUSH_NOTIFICATION(400, "이미 푸시 알림이 비활성화되어 있습니다."),
    ;


    private final int status;
    private final String message;
}
