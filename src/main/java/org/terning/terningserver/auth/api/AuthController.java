package org.terning.terningserver.auth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.auth.application.AuthService;
import org.terning.terningserver.auth.config.Login;
import org.terning.terningserver.auth.dto.request.FcmTokenSyncRequest;
import org.terning.terningserver.auth.dto.request.SignInRequest;
import org.terning.terningserver.auth.dto.request.SignUpFilterRequest;
import org.terning.terningserver.auth.dto.request.SignUpRequest;
import org.terning.terningserver.auth.dto.response.SignInResponse;
import org.terning.terningserver.auth.dto.response.SignUpResponse;
import org.terning.terningserver.auth.dto.response.TokenReissueResponse;
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
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SignInRequest request
    ) {
        SignInResponse response = authService.signIn(socialAccessToken, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_IN, response));
    }

    @PostMapping("/token-reissue")
    public ResponseEntity<SuccessResponse<TokenReissueResponse>> reissueToken(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        TokenReissueResponse response = authService.reissueAccessToken(authorizationHeader);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_REISSUE_TOKEN, response));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponse<SignUpResponse>> signUp(
            @RequestHeader("Authorization") String authId,
            @RequestBody SignUpRequest request
    ) {
        SignUpResponse signUpResponse = authService.signUp(authId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_UP, signUpResponse));
    }

    @PostMapping("/sign-up/filter")
    public ResponseEntity<SuccessResponse> registerUserFilter(
            @RequestHeader("User-Id") Long userId,
            @RequestBody SignUpFilterRequest request
    ) {
        authService.registerUserFilter(userId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_UP_FILTER));
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse> signOut(@Login Long userId) {

        authService.signOut(userId);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_OUT));
    }
    @DeleteMapping("/withdraw")
    public ResponseEntity<SuccessResponse> withdraw(@Login Long userId) {

        authService.withdraw(userId);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_WITHDRAW));
    }

    @PostMapping("/sync-user")
    public ResponseEntity<SuccessResponse> syncUser(
            @Login Long userId,
            @RequestBody FcmTokenSyncRequest request
    ) {
        authService.syncUser(userId, request);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_USER_SYNC));
    }
}
