package org.terning.terningserver.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.auth.application.reissue.AuthReissueService;
import org.terning.terningserver.auth.application.signin.AuthSignInService;
import org.terning.terningserver.auth.application.signout.AuthSignOutService;
import org.terning.terningserver.auth.application.signup.AuthSignUpService;
import org.terning.terningserver.auth.application.syncUser.AuthSyncUserService;
import org.terning.terningserver.auth.application.withdraw.AuthWithdrawService;
import org.terning.terningserver.auth.dto.request.FcmTokenSyncRequest;
import org.terning.terningserver.auth.dto.request.SignInRequest;
import org.terning.terningserver.auth.dto.request.SignUpFilterRequestDto;
import org.terning.terningserver.auth.dto.request.SignUpRequestDto;
import org.terning.terningserver.auth.dto.response.AccessTokenGetResponseDto;
import org.terning.terningserver.auth.dto.response.SignInResponse;
import org.terning.terningserver.auth.dto.response.SignUpResponseDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthSignInService authSignInService;
    private final AuthSignUpService authSignUpService;
    private final AuthSignOutService authSignOutService;
    private final AuthWithdrawService authWithdrawService;
    private final AuthReissueService authReissueService;
    private final AuthSyncUserService authSyncUserService;

    @Transactional
    public SignInResponse signIn(String authAccessToken, SignInRequest request) {
        SignInResponse signInResponse = authSignInService.signIn(authAccessToken, request);
        return signInResponse;
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

    @Transactional
    public void syncUser(long userId, FcmTokenSyncRequest request) {
        authSyncUserService.syncUser(userId, request);
    }
}
