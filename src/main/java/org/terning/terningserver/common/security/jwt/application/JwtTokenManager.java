package org.terning.terningserver.common.security.jwt.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.common.security.jwt.auth.UserAuthentication;
import org.terning.terningserver.common.security.jwt.auth.AuthenticationTokenFactory;
import org.terning.terningserver.common.config.ValueConfig;
import org.terning.terningserver.user.domain.Token;
import org.terning.terningserver.user.domain.User;

@Service
@RequiredArgsConstructor
public class JwtTokenManager {

    private final JwtTokenIssuer jwtTokenIssuer;
    private final ValueConfig valueConfig;

    public Token generateToken(User user) {
        UserAuthentication authentication = AuthenticationTokenFactory.create(user);

        long accessTokenExpiration = valueConfig.getAccessTokenExpired();
        long refreshTokenExpiration = valueConfig.getRefreshTokenExpired();

        return Token.builder()
                .accessToken(jwtTokenIssuer.generateToken(authentication, accessTokenExpiration))
                .refreshToken(jwtTokenIssuer.generateToken(authentication, refreshTokenExpiration))
                .build();
    }

    public Token issueAccessToken(User user) {
        UserAuthentication authentication = AuthenticationTokenFactory.create(user);

        long accessTokenExpiration = valueConfig.getAccessTokenExpired();

        return Token.builder()
                .accessToken(jwtTokenIssuer.generateToken(authentication, accessTokenExpiration))
                .build();
    }
}
