package org.terning.terningserver.auth.dto.response;

import org.terning.terningserver.auth.dto.Token;
import org.terning.terningserver.user.domain.AuthType;
import org.terning.terningserver.user.domain.User;

public record SignUpResponse(String accessToken, String refreshToken, Long userId, AuthType authType) {
    public static SignUpResponse of(Token token, User user) {
        return new SignUpResponse(
                token.accessToken(),
                token.refreshToken(),
                user.getId(),
                user.getAuthType()
        );
    }
}
