package org.terning.terningserver.auth.application.social.apple;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.auth.application.social.SocialAuthProvider;
import org.terning.terningserver.user.domain.AuthType;

@Service
@RequiredArgsConstructor
public class AppleAuthProvider implements SocialAuthProvider {

    private final AppleAuthTokenValidator appleAuthTokenValidator;

    @Override
    public String getAuthId(String authAccessToken) {
        return appleAuthTokenValidator.extractAppleId(authAccessToken);
    }

    @Override
    public AuthType supports() {
        return AuthType.APPLE;
    }
}
