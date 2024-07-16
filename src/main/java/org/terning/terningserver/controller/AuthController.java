package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.AuthSwagger;
import org.terning.terningserver.domain.Filter;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpRequestDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.dto.auth.response.SignUpFilterResponseDto;
import org.terning.terningserver.dto.auth.response.TokenGetResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.service.AuthService;
import org.terning.terningserver.service.SignUpFilterService;
import org.terning.terningserver.service.SignUpService;

import java.security.Principal;

import static org.terning.terningserver.exception.enums.SuccessMessage.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthSwagger {

    private final AuthService authService;
    private final SignUpService signUpService;
    private final SignUpFilterService signUpFilterService;

    @PostMapping("/sign-in")
    public ResponseEntity<SuccessResponse<SignInResponseDto>> signIn(
            @RequestHeader("Authorization") String authAccessToken,
            @RequestBody SignInRequestDto request
    ) {
        User user = authService.saveUser(authAccessToken, request);

        val signInResponse = authService.signIn(user, request);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_IN, signInResponse));
    }



    // TODO: 에러 메시지 위치
    @PostMapping("/token-reissue")
    public ResponseEntity<SuccessResponse<TokenGetResponseDto>> reissueToken(
            @RequestHeader("Authorization") String refreshToken
    ) {
        val response = authService.reissueToken(refreshToken);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_REISSUE_TOKEN, response));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SuccessResponse<SignInResponseDto>> signUp(
            @RequestHeader("User-Id") Long userId,
            @RequestBody SignUpRequestDto request
    ) {
        signUpService.signUp(userId, request.name(), request.profileImage());

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_UP));
    }

    @PostMapping("/sign-up/filter")
    public ResponseEntity<SuccessResponse<SignUpFilterResponseDto>> filter(
            @RequestHeader("User-Id") Long userId,
            @RequestBody SignUpFilterRequestDto request
    ) {
        // 필터 생성 및 저장
        Filter newFilter = signUpFilterService.createAndSaveFilter(request);

        // 사용자에게 필터 연결
        signUpFilterService.connectFilterToUser(userId, newFilter.getId());

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_UP_FILTER));
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse> signOut(Principal principal) {
        val userId = Long.parseLong(principal.getName());

        authService.signOut(userId);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_SIGN_OUT));
    }
    @DeleteMapping("/withdraw")
    public ResponseEntity<SuccessResponse> withdraw(Principal principal) {
        val userId = Long.parseLong(principal.getName());

        authService.withdraw(userId);

        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_WITHDRAW));
    }
}