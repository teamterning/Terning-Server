package org.terning.terningserver.common.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    // 홈 화면
    SUCCESS_GET_ANNOUNCEMENTS(200, "인턴 공고 불러오기를 성공했습니다"),
    SUCCESS_GET_UPCOMING_ANNOUNCEMENTS(200, "곧 마감인 인턴 공고 요청을 성공했습니다"),
    SUCCESS_GET_UPCOMING_ANNOUNCEMENTS_NO_SCRAP(200, "아직 스크랩된 인턴 공고가 없어요!"),
    SUCCESS_GET_UPCOMING_ANNOUNCEMENTS_EMPTY_LIST(200, "일주일 내에 마감인 공고가 없어요\n캘린더에서 스크랩한 공고 일정을 확인해 보세요"),

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
    SUCCESS_GET_BANNERS(200, "탐색 뷰 > 배너 조회에 성공했습니다"),

    // 인턴 공고
    SUCCESS_GET_INTERNSHIP_DETAIL(200, "공고 상세 정보 불러오기에 성공했습니다"),

    // 스크랩
    SUCCESS_CREATE_SCRAP(201, "스크랩 추가에 성공했습니다"),
    SUCCESS_DELETE_SCRAP(200, "스크랩 취소에 성공했습니다"),
    SUCCESS_UPDATE_SCRAP(200, "스크랩 수정에 성공했습니다"),

    // Calendar (캘린더 화면)
    SUCCESS_GET_MONTHLY_SCRAPS(200, "캘린더 > (월간) 스크랩 된 공고 정보 불러오기를 성공했습니다"),
    SUCCESS_GET_MONTHLY_SCRAPS_AS_LIST(200, "캘린더 > (월간) 스크랩 된 공고 정보 (리스트) 불러오기를 성공했습니다"),
    SUCCESS_GET_DAILY_SCRAPS(200, "캘린더 > (일간) 스크랩 된 공고 정보 불러오기를 성공했습니다"),

    // Filter(필터링)
    SUCCESS_GET_USER_FILTER(200, "사용자의 필터링 정보를 불러오는데 성공했습니다"),
    SUCCESS_UPDATE_USER_FILTER(200, "필터링 재설정에 성공했습니다"),

    // My page (마이페이지 화면)
    SUCCESS_GET_PROFILE(200, "마이페이지 > 프로필 정보 불러오기를 성공했습니다"),
    SUCCESS_UPDATE_PROFILE(200, "프로필 수정에 성공했습니다"),
    PUSH_STATUS_UPDATED(200, "사용자 푸시알림 여부 변경을 완료했습니다."),

    // 유저 동기화
    SUCCESS_USER_SYNC(201, "유저 동기화를 성공했습니다.");

    private final int status;
    private final String message;

}
