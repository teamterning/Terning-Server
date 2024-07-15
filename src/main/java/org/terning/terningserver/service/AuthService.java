package org.terning.terningserver.service;

import org.terning.terningserver.domain.User;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.dto.auth.response.TokenGetResponseDto;

public interface AuthService {

    SignInResponseDto signIn(User user, SignInRequestDto request);

    User saveUser(String authAccessToken, SignInRequestDto request);

    void signOut(long userId);

    void withdraw(long userId);

    TokenGetResponseDto reissueToken(String refreshToken);
}
