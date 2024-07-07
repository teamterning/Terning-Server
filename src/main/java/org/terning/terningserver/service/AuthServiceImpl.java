package org.terning.terningserver.service;

import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.auth.request.SignInServiceRequest;
import org.terning.terningserver.domain.auth.request.TokenGetServiceRequest;
import org.terning.terningserver.domain.auth.response.SignInServiceResponse;
import org.terning.terningserver.domain.auth.response.TokenGetServiceResponse;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.exception.UserException;
import org.terning.terningserver.jwt.JwtTokenProvider;
import org.terning.terningserver.jwt.UserAuthentication;
import org.terning.terningserver.repository.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.INVALID_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final KakaoService kakaoService;
    private final AppleService appleService;
    private final UserService userService;
    private final ValueConfig valueConfig;

    @Override
    @Transactional
    public SignInServiceResponse signIn(SignInServiceRequest request) {
        val user = getUser(request.authAccessToken(), request.authType());
        val token = getToken(user);
        return SignInServiceResponse.of(token);
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
    public TokenGetServiceResponse reissueToken(TokenGetServiceRequest request) {
        val user = findUser(request.refreshToken());
        val token = generateAccessToken(user.getId());
        return TokenGetServiceResponse.of(token);
    }

    private User getUser(String authAccessToken, AuthType authType) {
        val authId = getAuthId(authAccessToken, authType);
        return signUp(authType, authId);
    }

    private String getAuthId(String authAccessToken, AuthType authType) {
        return switch (authType) {
            case APPLE -> appleService.getAppleData(authAccessToken);
            case KAKAO -> kakaoService.getKakaoData(authAccessToken);
        };
    }

    private User signUp(AuthType authType, String authId) {
        return userRepository.findByAuthTypeAndAuthId(authType, authId)
                .orElseGet(() -> saveUser(authType, authId));
    }

    private User saveUser(AuthType authType, String authId) {
        val user = User.builder().authType(authType).authId(authId).build();
        return userRepository.save(user);
    }

    private Token getToken(User user) {
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
        return userRepository.findById(id).orElseThrow(() -> new UserException(INVALID_USER));
    }

    private User findUser(String refreshToken) {
        return userRepository.findByRefreshToken(getTokenFromBearerString(refreshToken))
                .orElseThrow(() -> new UserException(INVALID_USER));
    }

    private String getTokenFromBearerString(String token) {
        return token.replaceFirst(ValueConfig.BEARER_HEADER, ValueConfig.BLANK);
    }

    private String generateAccessToken(long userId) {
        val authentication = new UserAuthentication(userId, null, null);
        return jwtTokenProvider.generateToken(authentication, valueConfig.getAccessTokenExpired());
    }

    private void deleteUser(User user) {
        userService.deleteUser(user);
    }

}
