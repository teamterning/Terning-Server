package org.terning.terningserver.service;

import org.terning.terningserver.domain.auth.request.SignInServiceRequest;
import org.terning.terningserver.domain.auth.request.TokenGetRequest;
import org.terning.terningserver.domain.auth.response.SignInServiceResponse;
import org.terning.terningserver.domain.auth.response.TokenGetResponse;

public interface AuthService {

    SignInServiceResponse signIn(SignInServiceRequest request);

    void signOut(long memberId);

    void withdraw(long memberId);

    TokenGetResponse reissueToken(TokenGetRequest request);
}
