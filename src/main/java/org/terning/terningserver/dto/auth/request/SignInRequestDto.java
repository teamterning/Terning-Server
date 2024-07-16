package org.terning.terningserver.dto.auth.request;

import lombok.NonNull;
import org.terning.terningserver.domain.enums.AuthType;

public record SignInRequestDto(
        @NonNull AuthType authType
        ) {

    public static SignInRequestDto of(AuthType authType){
        return new SignInRequestDto(authType);
    }
}
