package org.terning.terningserver.auth.application.signin;

import org.terning.terningserver.auth.dto.response.SignInResponse;
import org.terning.terningserver.auth.dto.request.SignInRequest;

public interface AuthSignInService {
    SignInResponse signIn(String authAccessToken, SignInRequest request);
}
