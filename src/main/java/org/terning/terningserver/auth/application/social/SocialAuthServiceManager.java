package org.terning.terningserver.auth.application.social;

import org.terning.terningserver.user.domain.AuthType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SocialAuthServiceManager {

    private final Map<AuthType, SocialAuthProvider> authServiceMap;

    private SocialAuthServiceManager(Map<AuthType, SocialAuthProvider> authServiceMap) {
        this.authServiceMap = authServiceMap;
    }

    public static SocialAuthServiceManager create(List<SocialAuthProvider> socialAuthProviders) {
        Map<AuthType, SocialAuthProvider> authServiceMap = socialAuthProviders.stream()
                .collect(Collectors.toMap(SocialAuthProvider::supports, Function.identity()));
        return new SocialAuthServiceManager(authServiceMap);
    }

    public SocialAuthProvider getAuthService(AuthType authType) {
        SocialAuthProvider provider = authServiceMap.get(authType);
        if (provider == null) {
            throw new IllegalArgumentException("지원되지 않는 소셜 로그인 타입: " + authType);
        }
        return provider;
    }
}
