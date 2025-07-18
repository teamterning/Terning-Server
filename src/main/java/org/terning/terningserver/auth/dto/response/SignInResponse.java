package org.terning.terningserver.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.terning.terningserver.auth.dto.Token;
import org.terning.terningserver.user.domain.AuthType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SignInResponse(
        String accessToken,
        String refreshToken,
        String authId,
        AuthType authType,
        Long userId
) {
    public static SignInResponse ofExistingUser(Token token, String authId, AuthType authType, Long userId) {
        return new SignInResponse(
                token.accessToken(),
                token.refreshToken(),
                authId,
                authType,
                userId
        );
    }

    public static SignInResponse ofNewUser(String authId, AuthType authType) {
        return new SignInResponse(
                null,
                null,
                authId,
                authType,
                null
        );
    }
}
