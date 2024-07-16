package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.dto.auth.response.SignUpResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.user.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.FAILED_SIGN_UP;


@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final AuthServiceImpl authService;

    public SignUpResponseDto signUp(String authId, String name, Integer profileImage, AuthType authType) {
        Token token = authService.getTokenByAuthId(authId);
        User user = userRepository.save(User.builder()
                .authId(authId)
                .name(name)
                .authType(authType)
                .refreshToken(token.getRefreshToken())
                .profileImage(profileImage)
                .build());

        return SignUpResponseDto.of(token.getAccessToken(), token.getRefreshToken(), user.getId(), authType);
    }
}