package org.terning.terningserver.auth.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.terning.terningserver.auth.config.Login;
import org.terning.terningserver.auth.dto.request.FcmTokenSyncRequest;
import org.terning.terningserver.auth.dto.request.SignInRequest;
import org.terning.terningserver.auth.dto.request.SignUpFilterRequest;
import org.terning.terningserver.auth.dto.request.SignUpRequest;
import org.terning.terningserver.auth.dto.response.SignInResponse;
import org.terning.terningserver.auth.dto.response.SignUpResponse;
import org.terning.terningserver.auth.dto.response.TokenReissueResponse;
import org.terning.terningserver.common.exception.dto.SuccessResponse;

@Tag(name = "Auth", description = "소셜 로그인 및 회원가입 API")
public interface AuthSwagger {

    @Operation(summary = "소셜 로그인", description = "AuthType에 맞는 소셜 로그인 API")
    ResponseEntity<SuccessResponse<SignInResponse>> signIn(
            @Parameter(name = "Authorization", description = "", example = "authAccessToken")
            @RequestHeader("Authorization") String authAccessToken,
            @RequestBody SignInRequest request
    );

    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API")
    ResponseEntity<SuccessResponse<TokenReissueResponse>> reissueToken(
            @Parameter(name = "Authorization", description = "", example = "refreshToken")
            @RequestHeader("Authorization") String refreshToken
    );

    @Operation(summary = "사용자 필터링 정보 생성", description = "사용자 필터링 정보 생성 API")
    ResponseEntity<SuccessResponse> registerUserFilter(
            @Parameter(name = "User-Id", description = "", example = "userId")
            @RequestHeader("User-Id") Long userId,
            @RequestBody SignUpFilterRequest request
    );

    @Operation(summary = "회원가입", description = "회원가입 API")
    ResponseEntity<SuccessResponse<SignUpResponse>> signUp(
            @Parameter(name = "Authorization", description = "", example = "authId")
            @RequestHeader("authId") String authId,
            @RequestBody SignUpRequest request
    );

    @Operation(summary = "로그아웃", description = "로그아웃 API")
    ResponseEntity<SuccessResponse> signOut(
            @Parameter(hidden = true) @Login Long userId);

    @Operation(summary = "계정탈퇴", description = "계정탈퇴 API")
    ResponseEntity<SuccessResponse> withdraw(
            @Parameter(hidden = true) @Login Long userId);

    @Operation(summary = "유저동기화", description = "유저동기화 API")
    ResponseEntity<SuccessResponse> syncUser(
            @Parameter(hidden = true) @Login Long userId,
            @RequestBody FcmTokenSyncRequest request
    );
}
