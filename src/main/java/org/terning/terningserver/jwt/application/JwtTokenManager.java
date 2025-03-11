package org.terning.terningserver.jwt.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.jwt.auth.UserAuthentication;
import org.terning.terningserver.jwt.auth.AuthenticationTokenFactory;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.User;

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
