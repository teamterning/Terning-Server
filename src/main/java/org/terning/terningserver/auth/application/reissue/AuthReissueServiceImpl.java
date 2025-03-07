package org.terning.terningserver.auth.application.reissue;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.auth.jwt.JwtTokenProvider;
import org.terning.terningserver.auth.jwt.UserAuthentication;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.user.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.FAILED_TOKEN_REISSUE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthReissueServiceImpl implements AuthReissueService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ValueConfig valueConfig;
    private final UserRepository userRepository;

    @Override
    public AccessTokenGetResponseDto reissueToken(String refreshToken) {
        val user = findUserByRefreshToken(refreshToken);
        Token accessToken = getAccessToken(user);
        return AccessTokenGetResponseDto.of(accessToken);
    }

    private Token getAccessToken(User user) {
        String accessToken = createAccessToken(new UserAuthentication(user.getId(), null, null));
        return Token.builder()
                .accessToken(accessToken)
                .build();
    }

    private String createAccessToken(Authentication authentication) {
        return jwtTokenProvider.generateToken(authentication, valueConfig.getAccessTokenExpired());
    }

    private User findUserByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(getTokenFromBearerString(refreshToken))
                .orElseThrow(() -> new CustomException(FAILED_TOKEN_REISSUE));
    }

    private String getTokenFromBearerString(String token) {
        return token.replaceFirst(ValueConfig.BEARER_HEADER, ValueConfig.BLANK);
    }
}