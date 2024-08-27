package org.terning.terningserver.service;

import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.dto.auth.response.SignUpResponseDto;

public interface AuthService {

    SignInResponseDto signIn(String authAccessToken, SignInRequestDto request);

    SignUpResponseDto signUp(String authId, String name, Integer profileImage, AuthType authType);

    void signOut(long userId);

    void withdraw(long userId);

    AccessTokenGetResponseDto reissueToken(String refreshToken);
}
