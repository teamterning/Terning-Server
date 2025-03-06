package org.terning.terningserver.auth.application.signin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.auth.application.social.SocialAuthProvider;
import org.terning.terningserver.auth.application.social.SocialAuthServiceManager;
import org.terning.terningserver.auth.jwt.JwtTokenProvider;
import org.terning.terningserver.auth.jwt.UserAuthentication;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.repository.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthSignInServiceImpl implements AuthSignInService {

    private final SocialAuthServiceManager socialAuthServiceManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ValueConfig valueConfig;

    @Transactional
    @Override
    public SignInResponseDto signIn(String authAccessToken, SignInRequestDto request) {
        String authId = getAuthId(request.authType(), authAccessToken);


        return findUserByAuthIdAndType(authId, request.authType())
                .map(user -> createSignInResponseForExistingUser(user, authId, request.authType()))
                .orElseGet(() -> createSignInResponseForNonExistingUser(authId, request.authType()));
    }

    private String getAuthId(AuthType authType, String authAccessToken) {
        SocialAuthProvider provider = socialAuthServiceManager.getAuthService(authType);
        return provider.getAuthId(authAccessToken);
    }


    private Optional<User> findUserByAuthIdAndType(String authId, AuthType authType) {
        return userRepository.findByAuthIdAndAuthType(authId, authType);
    }

    private SignInResponseDto createSignInResponseForExistingUser(User user, String authId, AuthType authType) {
        Token token = getFullToken(user);
        user.updateRefreshToken(token.getRefreshToken());

        return SignInResponseDto.of(
                token,
                authId,
                authType,
                user.getId()
        );
    }

    private SignInResponseDto createSignInResponseForNonExistingUser(String authId, AuthType authType) {
        return SignInResponseDto.of(null, authId, authType, null);
    }

    private Token getFullToken(User user) {
        String accessToken = createAccessToken(new UserAuthentication(user.getId(), null, null));
        String refreshToken = createRefreshToken(new UserAuthentication(user.getId(), null, null));

        user.updateRefreshToken(refreshToken);

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String createAccessToken(UserAuthentication authentication) {
        return jwtTokenProvider.generateToken(authentication, valueConfig.getAccessTokenExpired());
    }

    private String createRefreshToken(UserAuthentication authentication) {
        return jwtTokenProvider.generateToken(authentication, valueConfig.getRefreshTokenExpired());
    }
}