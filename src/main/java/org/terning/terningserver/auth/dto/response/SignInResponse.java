package org.terning.terningserver.auth.dto.response;

import org.terning.terningserver.user.domain.Token;
import org.terning.terningserver.user.domain.AuthType;

import java.util.Optional;

public record SignInResponse(
        String accessToken,
        String refreshToken,
        Long userId,
        String authId,
        AuthType authType
//        boolean fcmTokenReissueRequired
) {
    public static SignInResponse of(Token token, String authId, AuthType authType, Long userId) {
        return new SignInResponse(
                Optional.ofNullable(token).map(Token::getAccessToken).orElse(null),
                Optional.ofNullable(token).map(Token::getRefreshToken).orElse(null),
                userId,
                authId,
                authType
//                fcmTokenReissueRequired
        );
    }
}
