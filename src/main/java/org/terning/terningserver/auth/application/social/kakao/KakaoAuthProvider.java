package org.terning.terningserver.auth.application.social.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.auth.application.social.SocialAuthProvider;
import org.terning.terningserver.user.domain.AuthType;

@Service
@RequiredArgsConstructor
public class KakaoAuthProvider implements SocialAuthProvider {

    private final KakaoAuthTokenValidator kakaoAuthTokenValidator;

    @Override
    public String getAuthId(String authAccessToken) {
        return kakaoAuthTokenValidator.extractKakaoId(authAccessToken);
    }

    @Override
    public AuthType supports() {
        return AuthType.KAKAO;
    }
}
