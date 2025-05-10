package org.terning.terningserver.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.common.exception.enums.ErrorMessage;

import java.util.Arrays;

public enum PushNotificationStatus {
    ENABLED("enabled"),
    DISABLED("disabled");

    private final String value;

    PushNotificationStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PushNotificationStatus from(String input) {
        return Arrays.stream(values())
                .filter(status -> status.value.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorMessage.INVALID_FORMAT_ERROR));
    }

    public static boolean isValid(String input) {
        return Arrays.stream(values())
                .anyMatch(status -> status.value.equalsIgnoreCase(input));
    }

    public boolean canReceiveNotification() {
        return this == ENABLED;
    }

    public PushNotificationStatus enable() {
        if (this == ENABLED) {
            throw new CustomException(ErrorMessage.ALREADY_ENABLED_PUSH_NOTIFICATION);
        }
        return ENABLED;
    }

    public PushNotificationStatus disable() {
        if (this == DISABLED) {
            throw new CustomException(ErrorMessage.ALREADY_DISABLED_PUSH_NOTIFICATION);
        }
        return DISABLED;
    }

    @JsonValue
    public String value() {
        return value;
    }
}
