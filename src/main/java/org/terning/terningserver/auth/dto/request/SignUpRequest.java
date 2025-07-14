package org.terning.terningserver.auth.dto.request;

import org.terning.terningserver.user.domain.AuthType;

public record SignUpRequest(String name, String profileImage, AuthType authType, String fcmToken) {
}
