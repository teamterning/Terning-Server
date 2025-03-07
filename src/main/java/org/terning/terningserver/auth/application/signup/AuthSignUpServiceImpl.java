package org.terning.terningserver.auth.application.signup;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.auth.jwt.JwtTokenProvider;
import org.terning.terningserver.auth.jwt.UserAuthentication;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.domain.Filter;
import org.terning.terningserver.domain.Token;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.ProfileImage;
import org.terning.terningserver.domain.enums.Grade;
import org.terning.terningserver.domain.enums.JobType;
import org.terning.terningserver.domain.enums.WorkingPeriod;
import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpRequestDto;
import org.terning.terningserver.dto.auth.request.SignUpWithAuthIdRequestDto;
import org.terning.terningserver.dto.auth.response.SignUpResponseDto;
import org.terning.terningserver.event.UserSignedUpEvent;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.filter.FilterRepository;
import org.terning.terningserver.repository.user.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthSignUpServiceImpl implements AuthSignUpService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ValueConfig valueConfig;
    private final UserRepository userRepository;
    private final FilterRepository filterRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public SignUpResponseDto signUp(String authId, SignUpRequestDto request) {
        String tokenWithoutBearer = authId.replace("Bearer ", "").trim();
        SignUpWithAuthIdRequestDto requestDto = createSignUpRequestDto(tokenWithoutBearer, request);
        User user = createUser(requestDto);
        Token token = getFullToken(user);

        eventPublisher.publishEvent(UserSignedUpEvent.of(user));

        return createSignUpResponseDto(token, user);
    }

    @Transactional
    @Override
    public void registerFilterWithUser(Long userId, SignUpFilterRequestDto request) {
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(FAILED_SIGN_UP_USER_FILTER_CREATION));

        Filter filter = buildFilterFromRequest(request);
        filterRepository.save(filter);

        user.assignFilter(filter);
        userRepository.save(user);
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
        ProfileImage profileImage = requestDto.profileImage() != null
                ? ProfileImage.fromValue(requestDto.profileImage()) : ProfileImage.BASIC;

        User user = User.builder()
                .authId(requestDto.authId())
                .name(requestDto.name())
                .authType(requestDto.authType())
                .profileImage(profileImage)
                .build();
        return userRepository.save(user);
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

    private SignUpResponseDto createSignUpResponseDto(Token token, User user) {
        return SignUpResponseDto.of(token.getAccessToken(), token.getRefreshToken(), user.getId(), user.getAuthType());
    }

    private String createAccessToken(UserAuthentication authentication) {
        return jwtTokenProvider.generateToken(authentication, valueConfig.getAccessTokenExpired());
    }

    private String createRefreshToken(UserAuthentication authentication) {
        return jwtTokenProvider.generateToken(authentication, valueConfig.getRefreshTokenExpired());
    }

    private Filter buildFilterFromRequest(SignUpFilterRequestDto request) {
        return Filter.builder()
                .jobType(JobType.TOTAL)
                .grade(Grade.fromKey(request.grade()))
                .workingPeriod(WorkingPeriod.fromKey(request.workingPeriod()))
                .startYear(request.startYear())
                .startMonth(request.startMonth())
                .build();
    }
}
