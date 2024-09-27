package org.terning.terningserver.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpRequestDto;
import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.dto.auth.response.SignUpFilterResponseDto;
import org.terning.terningserver.dto.auth.response.SignUpResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;

@Tag(name = "Auth", description = "소셜 로그인 및 회원가입 API")
public interface AuthSwagger {

    @Operation(summary = "소셜 로그인", description = "AuthType에 맞는 소셜 로그인 API")
    ResponseEntity<SuccessResponse<SignInResponseDto>> signIn(
            @Parameter(name = "Authorization", description = "", example = "authAccessToken")
            @RequestHeader("Authorization") String authAccessToken,
            @RequestBody SignInRequestDto request
    );

    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API")
    ResponseEntity<SuccessResponse<AccessTokenGetResponseDto>> reissueToken(
            @Parameter(name = "Authorization", description = "", example = "refreshToken")
            @RequestHeader("Authorization") String refreshToken
    );

    @Operation(summary = "사용자 필터링 정보 생성", description = "사용자 필터링 정보 생성 API")
    ResponseEntity<SuccessResponse<SignUpFilterResponseDto>> filter(
            @Parameter(name = "User-Id", description = "", example = "userId")
            @RequestHeader("User-Id") Long userId,
            @RequestBody SignUpFilterRequestDto request
    );

    @Operation(summary = "회원가입", description = "회원가입 API")
    ResponseEntity<SuccessResponse<SignUpResponseDto>> signUp(
            @Parameter(name = "Authorization", description = "", example = "authId")
            @RequestHeader("authId") String authId,
            @RequestBody SignUpRequestDto request
    );

    @Operation(summary = "로그아웃", description = "로그아웃 API")
    ResponseEntity<SuccessResponse> signOut(
            @AuthenticationPrincipal Long userId);

    @Operation(summary = "계정탈퇴", description = "계정탈퇴 API")
    ResponseEntity<SuccessResponse> withdraw(
            @AuthenticationPrincipal Long userId);


}
