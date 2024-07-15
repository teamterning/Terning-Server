package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    // 홈 화면
    SUCCESS_GET_ANNOUNCEMENTS(200, "인턴 공고 불러오기를 성공했습니다"),
    SUCCESS_GET_TODAY_ANNOUNCEMENTS(200, "오늘 마감인 인턴 공고 요청을 성공했습니다"),

    SUCCESS_CREATE_CATEGORY(201,  "카테고리를 성공적으로 추가하였습니다."),
    SUCCESS_GET_CATEGORIES(200,  "카테고리 리스트 조회를 성공하였습니다."),
    SUCCESS_CREATE_WORD(201, "단어를 성공적으로 추가하였습니다."),
    SUCCESS_GET_WORDS(200,  "단어 리스트 조회를 성공하였습니다."),
    SUCCESS_GET_WORD(200, "특정 단어 조회를 성공하였습니다."),

    // 소셜 로그인
    SUCCESS_SIGN_IN(200, "소셜 로그인에 성공하였습니다"),

    // 토큰 재발급
    SUCCESS_REISSUE_TOKEN(200, "토큰 재발급에 성공하였습니다."),

    // 회원가입
    SUCCESS_SIGN_UP(201, "회원가입에 성공하였습니다"),

    // 사용자 정보 필터링
    SUCCESS_SIGN_UP_FILTER(200, "사용자 필터링 정보 생성에 성공하였습니다"),

    // 로그 아웃
    SUCCESS_SIGN_OUT(200, "로그아웃에 성공하였습니다"),
    SUCCESS_REFRESH_TOKEN_RESET(200, "리프레쉬 토큰 초기화에 성공하였습니다"),

    // 계정 탈퇴
    SUCCESS_WITHDRAW(200, "계정 탈퇴에 성공하였습니다"),

    // Search (탐색 화면)
    SUCCESS_GET_MOST_VIEWED_ANNOUNCEMENTS(200, "탐색 > 조회수 많은 공고를 조회하는데 성공했습니다"),
    SUCCESS_GET_MOST_SCRAPPED_ANNOUNCEMENTS(200, "탐색 > 스크랩 수 많은 공고를 조회하는데 성공했습니다"),
    SUCCESS_GET_SEARCH_ANNOUNCEMENTS(200, "검색에 성공했습니다"),

    // 인턴 공고
    SUCCESS_GET_INTERNSHIP_DETAIL(200, "공고 상세 정보 불러오기에 성공했습니다"),

    // Calendar (캘린더 화면)
    SUCCESS_GET_MONTHLY_SCRAPS(200, "캘린더 > (월간) 스크랩 된 공고 정보 불러오기를 성공했습니다"),
    SUCCESS_GET_MONTHLY_SCRAPS_AS_LIST(200, "캘린더 > (월간) 스크랩 된 공고 정보 (리스트) 불러오기를 성공했습니다"),
    SUCCESS_GET_DAILY_SCRAPS(200, "캘린더 > (일간) 스크랩 된 공고 정보 불러오기를 성공했습니다"),
  
    // Mypage (마이페이지 화면)
    SUCCESS_GET_PROFILE(200, "마이페이지 > 프로필 정보 불러오기를 성공했습니다"),
    ;

    private final int status;
    private final String message;

}
