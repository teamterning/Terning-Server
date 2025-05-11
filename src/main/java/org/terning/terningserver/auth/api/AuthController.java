package org.terning.terningserver.auth.api;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.auth.application.AuthService;
import org.terning.terningserver.auth.dto.request.FcmTokenSyncRequest;
import org.terning.terningserver.auth.dto.request.SignInRequest;
import org.terning.terningserver.auth.dto.request.SignUpFilterRequestDto;
import org.terning.terningserver.auth.dto.request.SignUpRequestDto;
import org.terning.terningserver.auth.dto.response.AccessTokenGetResponseDto;
import org.terning.terningserver.auth.dto.response.SignInResponse;
import org.terning.terningserver.auth.dto.response.SignUpResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;

import static org.terning.terningserver.auth.common.success.AuthSuccessCode.SUCCESS_SIGN_IN;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_REISSUE_TOKEN;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_SIGN_OUT;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_SIGN_UP;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_SIGN_UP_FILTER;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_USER_SYNC;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_WITHDRAW;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthSwagger {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<SuccessResponse<SignInResponse>> signIn(
            @RequestHeader("Authorization") String authAccessToken,
            @RequestBody SignInRequest request
    ) {
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_IN, authService.signIn(authAccessToken, request)));
    }

    @PostMapping("/token-reissue")
    public ResponseEntity<SuccessResponse<AccessTokenGetResponseDto>> reissueToken(
            @RequestHeader("Authorization") String refreshToken
    ) {
        val response = authService.reissueToken(refreshToken);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_REISSUE_TOKEN, response));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponse<SignUpResponseDto>> signUp(
            @RequestHeader("Authorization") String authId,
            @RequestBody SignUpRequestDto request
    ) {
        SignUpResponseDto signUpResponseDto = authService.signUp(authId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_UP, signUpResponseDto));
    }

    @PostMapping("/sign-up/filter")
    public ResponseEntity<SuccessResponse> registerUserFilter(
            @RequestHeader("User-Id") Long userId,
            @RequestBody SignUpFilterRequestDto request
    ) {
        authService.registerFilterWithUser(userId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_UP_FILTER));
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse> signOut(@AuthenticationPrincipal Long userId) {

        authService.signOut(userId);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_OUT));
    }
    @DeleteMapping("/withdraw")
    public ResponseEntity<SuccessResponse> withdraw(@AuthenticationPrincipal Long userId) {

        authService.withdraw(userId);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_WITHDRAW));
    }

    @PostMapping("/sync-user")
    public ResponseEntity<SuccessResponse> syncUser(
            @AuthenticationPrincipal Long userId,
            @RequestBody FcmTokenSyncRequest request
    ) {
        authService.syncUser(userId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_USER_SYNC));
    }
}
