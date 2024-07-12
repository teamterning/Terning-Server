package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    // Search (탐색 화면)
    SUCCESS_GET_MOST_VIEWED_ANNOUNCEMENTS(200, "탐색 > 조회수 많은 공고를 조회하는데 성공했습니다."),
    SUCCESS_GET_MOST_SCRAPPED_ANNOUNCEMENTS(200, "탐색 > 스크랩 수 많은 공고를 조회하는데 성공했습니다"),

    // 인턴 공고
    SUCCESS_GET_INTERNSHIP_DETAIL(200, "공고 상세 정보 불러오기에 성공했습니다");

    private final int status;
    private final String message;

}
