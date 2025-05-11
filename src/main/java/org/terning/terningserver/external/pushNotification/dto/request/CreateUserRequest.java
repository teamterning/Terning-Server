package org.terning.terningserver.external.pushNotification.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.terning.terningserver.user.domain.AuthType;

public record CreateUserRequest(
        @JsonProperty("oUserId")
        Long userId,
        String name,
        AuthType authType,
        String fcmToken,
        String pushStatus,
        String accountStatus
) {
}
