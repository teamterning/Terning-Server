package org.terning.terningserver.external.dto.request;

import org.terning.terningserver.domain.enums.AuthType;

public record CreateUserRequest(
        Long userId,
        String name,
        AuthType authType,
        String fcmToken,
        String pushStatus,
        String accountStatus
) {
}
