package org.terning.terningserver.external.pushNotification.scrap.common.failure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScrapExternalErrorCode {
    FAILED_TO_SEND_NOTIFICATION(HttpStatus.INTERNAL_SERVER_ERROR, "알림 서버 전송에 실패했습니다."),
    EMPTY_USER_IDS(HttpStatus.BAD_REQUEST, "전송할 유저 ID가 비어 있습니다.");

    public static final String PREFIX = "[SCRAP EXTERNAL ERROR]";

    private final HttpStatus status;
    private final String rawMessage;

    public String getMessage() {
        return PREFIX + " " + rawMessage;
    }
}
