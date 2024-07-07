package org.terning.terningserver.service;

import org.terning.terningserver.exception.dto.auth.request.SignInRequest;
import org.terning.terningserver.exception.dto.auth.request.TokenGetRequest;
import org.terning.terningserver.exception.dto.auth.response.SignInResponse;
import org.terning.terningserver.exception.dto.auth.response.TokenGetResponse;

public interface AuthService {

    SignInResponse signIn(SignInRequest request);

    void signOut(long memberId);

    void withdraw(long memberId);

    TokenGetResponse reissueToken(TokenGetRequest request);
}
