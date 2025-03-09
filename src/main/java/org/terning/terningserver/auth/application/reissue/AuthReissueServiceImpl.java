package org.terning.terningserver.auth.application.reissue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.jwt.application.JwtTokenManager;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.jwt.parser.TokenExtractor;
import org.terning.terningserver.repository.user.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.FAILED_TOKEN_REISSUE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthReissueServiceImpl implements AuthReissueService {

    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;

    @Override
    public AccessTokenGetResponseDto reissueToken(String refreshToken) {
        User user = findUserByRefreshToken(refreshToken);
        Token accessToken = jwtTokenManager.issueAccessToken(user);
        return AccessTokenGetResponseDto.of(accessToken);
    }

    private User findUserByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(TokenExtractor.extractToken(refreshToken))
                .orElseThrow(() -> new CustomException(FAILED_TOKEN_REISSUE));
    }
}
