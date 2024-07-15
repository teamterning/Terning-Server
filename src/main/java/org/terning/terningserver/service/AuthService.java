package org.terning.terningserver.service;

import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.auth.request.SignInRequest;
import org.terning.terningserver.domain.auth.response.SignInResponse;
import org.terning.terningserver.domain.auth.response.TokenGetResponse;

public interface AuthService {

    SignInResponse signIn(User user, SignInRequest request);

    User saveUser(String authAccessToken, SignInRequest request);

    void signOut(long userId);

    void withdraw(long userId);

    TokenGetResponse reissueToken(String refreshToken);
}
