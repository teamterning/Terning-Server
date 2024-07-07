package org.terning.terningserver.service;

import org.terning.terningserver.domain.auth.request.SignInServiceRequest;
import org.terning.terningserver.domain.auth.request.TokenGetServiceRequest;
import org.terning.terningserver.domain.auth.response.SignInServiceResponse;
import org.terning.terningserver.domain.auth.response.TokenGetServiceResponse;

public interface AuthService {

    SignInServiceResponse signIn(SignInServiceRequest request);

    void signOut(long userId);

    void withdraw(long userId);

    TokenGetServiceResponse reissueToken(TokenGetServiceRequest request);
}
