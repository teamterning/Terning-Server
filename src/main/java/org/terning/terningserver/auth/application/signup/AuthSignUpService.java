package org.terning.terningserver.auth.application.signup;

import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpRequestDto;
import org.terning.terningserver.dto.auth.response.SignUpResponseDto;

public interface AuthSignUpService {
    SignUpResponseDto signUp(String authId, SignUpRequestDto request);
    void registerFilterWithUser(Long userId, SignUpFilterRequestDto request);
}
