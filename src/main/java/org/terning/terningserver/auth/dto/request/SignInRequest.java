package org.terning.terningserver.auth.dto.request;

import lombok.NonNull;
import org.terning.terningserver.domain.enums.AuthType;

public record SignInRequest(@NonNull AuthType authType, String fcmToken) {
    public static SignInRequest of(AuthType authType, String fcmToken){
        return new SignInRequest(authType, fcmToken);
    }
}
