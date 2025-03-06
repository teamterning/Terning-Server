package org.terning.terningserver.auth.application.signin;

import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;

public interface AuthSignInService {
    SignInResponseDto signIn(String authAccessToken, SignInRequestDto request);
}
