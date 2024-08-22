package org.terning.terningserver.service;

import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;

public interface AuthService {

    SignInResponseDto signIn(String authAccessToken, SignInRequestDto request);

    void signOut(long userId);

    void withdraw(long userId);

    AccessTokenGetResponseDto reissueToken(String refreshToken);
}
