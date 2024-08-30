package org.terning.terningserver.service;

import org.terning.terningserver.domain.Filter;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpWithAuthIdRequestDto;
import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.dto.auth.response.SignUpResponseDto;

public interface AuthService {

    SignInResponseDto signIn(String authAccessToken, SignInRequestDto request);

    SignUpResponseDto signUp(String authId, SignUpRequestDto request);

    void signOut(long userId);

    void withdraw(long userId);

    AccessTokenGetResponseDto reissueToken(String refreshToken);

    Filter createAndSaveFilter(SignUpFilterRequestDto request);

    void connectFilterToUser(long userId, long filterId);

}
