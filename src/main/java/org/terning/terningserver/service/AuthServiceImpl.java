package org.terning.terningserver.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.domain.Filter;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.Grade;
import org.terning.terningserver.domain.enums.JobType;
import org.terning.terningserver.domain.enums.ProfileImage;
import org.terning.terningserver.domain.enums.WorkingPeriod;
import org.terning.terningserver.dto.auth.request.SignInRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpWithAuthIdRequestDto;
import org.terning.terningserver.dto.auth.response.AccessTokenGetResponseDto;
import org.terning.terningserver.dto.auth.response.SignInResponseDto;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.dto.auth.response.SignUpResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.jwt.JwtTokenProvider;
import org.terning.terningserver.jwt.UserAuthentication;
import org.terning.terningserver.repository.filter.FilterRepository;
import org.terning.terningserver.repository.user.UserRepository;
import java.util.Optional;

import static org.terning.terningserver.exception.enums.ErrorMessage.*;

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
    private final FilterRepository filterRepository;
    private final WebhookService webhookService;

    @Override
    @Transactional
    public SignInResponseDto signIn(String authAccessToken, SignInRequestDto request) {
        String authId = getAuthId(request.authType(), authAccessToken);

        return findUserByAuthIdAndType(authId, request.authType())
                .map(user -> createSignInResponseForExistingUser(user, authId, request.authType()))
                .orElseGet(() -> createSignInResponseForNonExistingUser(authId, request.authType()));
    }

    @Transactional
    public SignUpResponseDto signUp(String authId, SignUpRequestDto request) {
        String tokenWithoutBearer = authId.replace("Bearer ", "").trim();

        SignUpWithAuthIdRequestDto requestDto = createSignUpRequestDto(tokenWithoutBearer, request);

        User user = createUser(requestDto);

        Token token = getFullToken(user);

        webhookService.sendDiscordNotification(user); // 디스코드에 회원가입 알림 전송

        return createSignUpResponseDto(token, user);
    }

    @Override
    @Transactional
    public void signOut(long userId) {
        val user = findUserById(userId);
        user.resetRefreshToken();
    }

    @Override
    @Transactional
    public void withdraw(long userId) {
        val user = findUserById(userId);
        deleteUser(user);
    }

    @Override
    public AccessTokenGetResponseDto reissueToken(String refreshToken) {
        val user = findUserByRefreshToken(refreshToken);
        Token accessToken = getAccessToken(user);
        return AccessTokenGetResponseDto.of(accessToken);
    }

    @Transactional
    public Filter createAndSaveFilter(SignUpFilterRequestDto request) {
        Filter filter = buildFilterFromRequest(request);
        return filterRepository.save(filter);
    }

    @Transactional
    public void connectFilterToUser(long userId, long filterId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(FAILED_SIGN_UP_USER_FILTER_CREATION));
        Filter filter = filterRepository.findById(filterId).orElseThrow(() -> new CustomException(FAILED_SIGN_UP_USER_FILTER_ASSIGNMENT));

        user.assignFilter(filter);

        userRepository.save(user);
    }

    protected Optional<User> findUserByAuthIdAndType(String authId, AuthType authType) {
        return userRepository.findByAuthIdAndAuthType(authId, authType);
    }

    @Transactional
    protected SignInResponseDto createSignInResponseForExistingUser(User user, String authId, AuthType authType) {
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

    private String getAuthId(AuthType authType, String authAccessToken) {
        return switch (authType) {
            case APPLE -> appleService.getAppleData(authAccessToken);
            case KAKAO -> kakaoService.getKakaoData(authAccessToken);
        };
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

    private SignUpWithAuthIdRequestDto createSignUpRequestDto(String authId, SignUpRequestDto request) {
        return SignUpWithAuthIdRequestDto.of(
                authId,
                request.name(),
                request.profileImage(),
                request.authType()
        );
    }

    private User createUser(SignUpWithAuthIdRequestDto requestDto) {
        //프로필 이미지가 null일 경우 기본값 "basic"으로 설정
        ProfileImage profileImage = requestDto.profileImage() != null
                ? ProfileImage.fromValue(requestDto.profileImage()) : ProfileImage.BASIC;

        User user = User.builder()
                .authId(requestDto.authId())
                .name(requestDto.name())
                .authType(requestDto.authType())
                .profileImage(profileImage) //String to Enum
                .build();
        return userRepository.save(user);
    }

    private SignUpResponseDto createSignUpResponseDto(Token token, User user) {
        return SignUpResponseDto.of(token.getAccessToken(), token.getRefreshToken(), user.getId(), user.getAuthType());
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

    private String createRefreshToken(Authentication authentication) {
        return jwtTokenProvider.generateToken(authentication, valueConfig.getRefreshTokenExpired());
    }

    private User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(INVALID_USER));
    }

    private User findUserByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(getTokenFromBearerString(refreshToken))
                .orElseThrow(() -> new CustomException(FAILED_TOKEN_REISSUE));
    }


    private String getTokenFromBearerString(String token) {
        return token.replaceFirst(ValueConfig.BEARER_HEADER, ValueConfig.BLANK);
    }

    private void deleteUser(User user) {
        userService.deleteUser(user);
    }

    private Filter buildFilterFromRequest(SignUpFilterRequestDto request) {
        JobType jobType = JobType.TOTAL;
        Grade grade = Grade.fromKey(request.grade());
        WorkingPeriod workingPeriod = WorkingPeriod.fromKey(request.workingPeriod());
        int startYear = request.startYear();
        int startMonth = request.startMonth();

        return Filter.builder()
                .jobType(jobType)
                .grade(grade)
                .workingPeriod(workingPeriod)
                .startYear(startYear)
                .startMonth(startMonth)
                .build();
    }
}