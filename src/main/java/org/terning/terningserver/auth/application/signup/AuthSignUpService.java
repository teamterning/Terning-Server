package org.terning.terningserver.auth.application.signup;

import org.terning.terningserver.auth.dto.request.SignUpFilterRequestDto;
import org.terning.terningserver.auth.dto.request.SignUpRequestDto;
import org.terning.terningserver.auth.dto.response.SignUpResponseDto;

public interface AuthSignUpService {
    SignUpResponseDto signUp(String authId, SignUpRequestDto request);
    void registerFilterWithUser(Long userId, SignUpFilterRequestDto request);
}
