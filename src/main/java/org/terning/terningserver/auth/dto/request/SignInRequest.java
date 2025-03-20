package org.terning.terningserver.auth.dto.request;

import lombok.NonNull;
import org.terning.terningserver.domain.enums.AuthType;

public record SignInRequest(@NonNull AuthType authType) {
    public static SignInRequest of(AuthType authType){
        return new SignInRequest(authType);
    }
}
