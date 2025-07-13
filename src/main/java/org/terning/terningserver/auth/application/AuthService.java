package org.terning.terningserver.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.auth.application.social.SocialAuthProvider;
import org.terning.terningserver.auth.application.social.SocialAuthServiceManager;
import org.terning.terningserver.auth.common.exception.AuthErrorCode;
import org.terning.terningserver.auth.common.exception.AuthException;
import org.terning.terningserver.auth.dto.Token;
import org.terning.terningserver.auth.dto.request.FcmTokenSyncRequest;
import org.terning.terningserver.auth.dto.request.SignInRequest;
import org.terning.terningserver.auth.dto.request.SignUpFilterRequest;
import org.terning.terningserver.auth.dto.request.SignUpRequest;
import org.terning.terningserver.auth.dto.response.SignInResponse;
import org.terning.terningserver.auth.dto.response.SignUpResponse;
import org.terning.terningserver.auth.dto.response.TokenReissueResponse;
import org.terning.terningserver.auth.jwt.JwtProvider;
import org.terning.terningserver.auth.jwt.exception.JwtErrorCode;
import org.terning.terningserver.auth.jwt.exception.JwtException;
import org.terning.terningserver.external.pushNotification.notification.NotificationUserClient;
import org.terning.terningserver.filter.domain.Filter;
import org.terning.terningserver.filter.repository.FilterRepository;
import org.terning.terningserver.user.application.UserService;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.user.event.UserSignedUpEvent;
import org.terning.terningserver.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final SocialAuthServiceManager socialAuthServiceManager;
    private final ApplicationEventPublisher eventPublisher;
    private final NotificationUserClient notificationUserClient;
    private final FilterRepository filterRepository;

    @Transactional
    public SignInResponse signIn(String socialAccessToken, SignInRequest request) {
        SocialAuthProvider provider = socialAuthServiceManager.getAuthService(request.authType());
        String authId = provider.getAuthId(socialAccessToken);

        User user = userRepository.findByAuthIdAndAuthType(authId, request.authType())
                .orElse(null);

        if (user == null) {
            return SignInResponse.ofNewUser(authId, request.authType());
        }

        Token token = jwtProvider.generateTokens(user.getId());
        user.updateRefreshToken(token.refreshToken());
        userRepository.save(user);

        return SignInResponse.ofExistingUser(token, authId, request.authType(), user.getId());
    }

    @Transactional
    public SignUpResponse signUp(String authId, SignUpRequest request) {
        if (userRepository.existsByAuthIdAndAuthType(authId, request.authType())) {
            throw new AuthException(AuthErrorCode.USER_ALREADY_EXIST);
        }

        User newUser = User.from(request);

        Token token = jwtProvider.generateTokens(newUser.getId());

        newUser.updateRefreshToken(token.refreshToken());

        userRepository.save(newUser);

        eventPublisher.publishEvent(UserSignedUpEvent.of(newUser));

        notificationUserClient.createUserOnNotificationServer(
                newUser.getId(),
                newUser.getName(),
                newUser.getAuthType(),
                request.fcmToken()
        );

        return SignUpResponse.of(token, newUser);
    }

    @Transactional
    public void registerUserFilter(Long userId, SignUpFilterRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        Filter newFilter = Filter.from(request);
        filterRepository.save(newFilter);

        user.assignFilter(newFilter);
    }

    @Transactional
    public void signOut(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));
        user.resetRefreshToken();
    }

    @Transactional
    public void withdraw(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));
        userService.deleteUser(user);
    }

    @Transactional
    public TokenReissueResponse reissueAccessToken(String authorizationHeader) {
        Long userId = jwtProvider.getUserIdFrom(authorizationHeader);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new JwtException(JwtErrorCode.INVALID_JWT_TOKEN));

        String providedToken = jwtProvider.resolveToken(authorizationHeader);
        user.validateRefreshToken(providedToken);

        Token accessToken = jwtProvider.generateAccessToken(userId);


        return new TokenReissueResponse(accessToken.accessToken());
    }

    @Transactional
    public void syncUser(long userId, FcmTokenSyncRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        notificationUserClient.createOrUpdateUser(user, request.fcmToken());
    }
}
