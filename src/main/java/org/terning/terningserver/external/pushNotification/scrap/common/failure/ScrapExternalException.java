package org.terning.terningserver.external.pushNotification.scrap.common.failure;

import lombok.Getter;

@Getter
public class ScrapExternalException extends RuntimeException {

    private final ScrapExternalErrorCode errorCode;

    public ScrapExternalException(ScrapExternalErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}