package org.terning.terningserver.auth.application.signin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.auth.application.social.SocialAuthProvider;
import org.terning.terningserver.auth.application.social.SocialAuthServiceManager;
import org.terning.terningserver.external.pushNotification.notification.NotificationUserClient;
import org.terning.terningserver.common.security.jwt.application.JwtTokenManager;
import org.terning.terningserver.user.domain.Token;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.user.domain.AuthType;
import org.terning.terningserver.auth.dto.request.SignInRequest;
import org.terning.terningserver.auth.dto.response.SignInResponse;
import org.terning.terningserver.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthSignInServiceImpl implements AuthSignInService {

    private final SocialAuthServiceManager socialAuthServiceManager;
    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;
    private final NotificationUserClient notificationUserClient;
//    private final FcmTokenValidationClient fcmTokenValidationClient;

    @Transactional
    @Override
    public SignInResponse signIn(String authAccessToken, SignInRequest request) {
        String authId = getAuthId(request.authType(), authAccessToken);
        User user = findUserByAuthIdAndType(authId, request.authType());

        if (user == null) {
//            return SignInResponse.of(null, authId, request.authType(), null, false);
            return SignInResponse.of(null, authId, request.authType(), null);
        }

        Token token = jwtTokenManager.generateToken(user);
        user.updateRefreshToken(token.getRefreshToken());

//        boolean fcmReissueRequired = fcmTokenValidationClient.requestFcmTokenValidation(user.getId());

        if (request.fcmToken() != null && !request.fcmToken().trim().isEmpty()) {
            notificationUserClient.createOrUpdateUser(user, request.fcmToken());
        }

//        return SignInResponse.of(token, authId, request.authType(), user.getId(), fcmReissueRequired);
        return SignInResponse.of(token, authId, request.authType(), user.getId());
    }


    private String getAuthId(AuthType authType, String authAccessToken) {
        SocialAuthProvider provider = socialAuthServiceManager.getAuthService(authType);
        return provider.getAuthId(authAccessToken);
    }

    private User findUserByAuthIdAndType(String authId, AuthType authType) {
        return userRepository.findByAuthIdAndAuthType(authId, authType).orElse(null);
    }
}
