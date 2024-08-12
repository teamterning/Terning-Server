package org.terning.terningserver.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.dto.auth.response.TokenGetResponseDto;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.jwt.JwtTokenProvider;
import org.terning.terningserver.jwt.UserAuthentication;
import org.terning.terningserver.repository.user.UserRepository;
import java.util.Optional;

import static org.terning.terningserver.exception.enums.ErrorMessage.INVALID_USER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoService kakaoService;
    private final AppleService appleService;
    private final UserService userService;
    private final ValueConfig valueConfig;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SignInResponseDto signIn(String authAccessToken, SignInRequestDto request) {
        String authId = getAuthId(request.authType(), authAccessToken);
        Optional<User> userOptional = userRepository.findByAuthIdAndAuthType(authId, request.authType());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Token token = getToken(user);
            user.updateRefreshToken(token.getRefreshToken());
            return SignInResponseDto.of(
                    token,
                    authId,
                    request.authType(),
                    user.getId()
            );
        }
        else {
            return SignInResponseDto.of(null, authId, request.authType(), null);
        }
    }

    @Transactional
    public User saveUser(SignInRequestDto request) {
        User user = User.builder()
                .authType(request.authType())
                .build();
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void signOut(long userId) {
        val user = findUser(userId);
        user.resetRefreshToken();
    }

    @Override
    @Transactional
    public void withdraw(long userId) {
        val user = findUser(userId);
        deleteUser(user);
    }

    @Override
    public TokenGetResponseDto reissueToken(String refreshToken) {
        val user = findUser(refreshToken);
        Token token = getToken(user);
        user.updateRefreshToken(token.getRefreshToken());
        return TokenGetResponseDto.of(token);
    }

    private String getAuthId(AuthType authType, String authAccessToken) {
        return switch (authType) {
            case APPLE -> appleService.getAppleData(authAccessToken);
            case KAKAO -> kakaoService.getKakaoData(authAccessToken);
        };
    }

    public Token getToken(User user) {
        val token = generateToken(new UserAuthentication(user.getId(), null, null));
        user.updateRefreshToken(token.getRefreshToken());
        return token;
    }

    private Token generateToken(Authentication authentication) {
        return Token.builder()
                .accessToken(jwtTokenProvider.generateToken(authentication, valueConfig.getAccessTokenExpired()))
                .refreshToken(jwtTokenProvider.generateToken(authentication, valueConfig.getRefreshTokenExpired()))
                .build();
    }

    private User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(INVALID_USER));
    }

    private User findUser(String refreshToken) {
        return userRepository.findByRefreshToken(getTokenFromBearerString(refreshToken))
                .orElseThrow(() -> new CustomException(INVALID_USER));
    }

    private String getTokenFromBearerString(String token) {
        return token.replaceFirst(ValueConfig.BEARER_HEADER, ValueConfig.BLANK);
    }

    private void deleteUser(User user) {
        userService.deleteUser(user);
    }
}
