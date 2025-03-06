package org.terning.terningserver.auth.api;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.auth.application.AuthService;
import org.terning.terningserver.controller.swagger.AuthSwagger;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpRequestDto;
import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.dto.auth.response.SignUpResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;

import static org.terning.terningserver.exception.enums.SuccessMessage.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthSwagger {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<SuccessResponse<SignInResponseDto>> signIn(
            @RequestHeader("Authorization") String authAccessToken,
            @RequestBody SignInRequestDto request
    ) {
        String extractedToken = authAccessToken.substring(7);

        if (extractedToken.split("\\.").length != 3) {
            return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_IN, authService.signIn(extractedToken, request)));
        }
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_IN, authService.signIn(extractedToken, request)));
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
}
