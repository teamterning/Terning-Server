package org.terning.terningserver.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.auth.application.reissue.AuthReissueService;
import org.terning.terningserver.auth.application.signin.AuthSignInService;
import org.terning.terningserver.auth.application.signout.AuthSignOutService;
import org.terning.terningserver.auth.application.signup.AuthSignUpService;
import org.terning.terningserver.auth.application.withdraw.AuthWithdrawService;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpRequestDto;
import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.dto.auth.response.SignUpResponseDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthSignInService authSignInService;
    private final AuthSignUpService authSignUpService;
    private final AuthSignOutService authSignOutService;
    private final AuthWithdrawService authWithdrawService;
    private final AuthReissueService authReissueService;

    @Transactional
    public SignInResponseDto signIn(String authAccessToken, SignInRequestDto request) {
        SignInResponseDto signInResponseDto = authSignInService.signIn(authAccessToken, request);
        return signInResponseDto;
    }

    @Transactional
    public SignUpResponseDto signUp(String authId, SignUpRequestDto request) {
        SignUpResponseDto signUpResponseDto = authSignUpService.signUp(authId, request);
        return signUpResponseDto;
    }

    @Transactional
    public void registerFilterWithUser(Long userId, SignUpFilterRequestDto request) {
        authSignUpService.registerFilterWithUser(userId, request);
    }

    @Transactional
    public void signOut(long userId) {
        authSignOutService.signOut(userId);
    }

    @Transactional
    public void withdraw(long userId) {
        authWithdrawService.withdraw(userId);
    }

    @Transactional
    public AccessTokenGetResponseDto reissueToken(String refreshToken) {
        AccessTokenGetResponseDto accessTokenGetResponseDto = authReissueService.reissueToken(refreshToken);
        return accessTokenGetResponseDto;
    }
}
