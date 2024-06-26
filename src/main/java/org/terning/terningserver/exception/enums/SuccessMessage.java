package org.terning.terningserver.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    SUCCESS_CREATE_CATEGORY(201, true, "카테고리를 성공적으로 추가하였습니다."),
    SUCCESS_GET_CATEGORIES(200, true, "카테고리 리스트 조회를 성공하였습니다."),
    SUCCESS_CREATE_WORD(201, true, "단어를 성공적으로 추가하였습니다."),
    SUCCESS_GET_WORDS(200, true, "단어 리스트 조회를 성공하였습니다."),
    SUCCESS_GET_MEMORIZED_WORDS(200, true, "외운 단어 리스트 조회를 성공하였습니다."),
    SUCCESS_GET_WORD(200, true, "특정 단어 조회를 성공하였습니다.");

    private final int status;
    private final boolean success;
    private final String message;

}
