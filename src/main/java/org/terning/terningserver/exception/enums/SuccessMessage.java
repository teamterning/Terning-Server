package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    // 홈 화면
    SUCCESS_GET_ANNOUNCEMENTS(200, "인턴 공고 불러오기를 성공했습니다"),
    SUCCESS_GET_TODAY_ANNOUNCEMENTS(200, "오늘 마감인 인턴 공고 요청을 성공했습니다"),

    // Search (탐색 화면)
    SUCCESS_GET_MOST_VIEWED_ANNOUNCEMENTS(200, "탐색 > 조회수 많은 공고를 조회하는데 성공했습니다"),
    SUCCESS_GET_MOST_SCRAPPED_ANNOUNCEMENTS(200, "탐색 > 스크랩 수 많은 공고를 조회하는데 성공했습니다"),

    // 인턴 공고
    SUCCESS_GET_INTERNSHIP_DETAIL(200, "공고 상세 정보 불러오기에 성공했습니다"),

    // Calendar (캘린더 화면)
    SUCCESS_GET_MONTHLY_SCRAPS(200, "캘린더 > (월간) 스크랩 된 공고 정보 불러오기를 성공했습니다"),
    SUCCESS_GET_MONTHLY_SCRAPS_AS_LIST(200, "캘린더 > (월간) 스크랩 된 공고 정보 (리스트) 불러오기를 성공했습니다"),
    SUCCESS_GET_DAILY_SCRAPS(200, "캘린더 > (일간) 스크랩 된 공고 정보 불러오기를 성공했습니다"),
    ;

    private final int status;
    private final String message;

}
