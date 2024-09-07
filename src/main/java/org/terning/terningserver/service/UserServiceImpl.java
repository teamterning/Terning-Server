package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.ProfileImage;
import org.terning.terningserver.dto.user.request.ProfileUpdateRequestDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.exception.enums.ErrorMessage;
import org.terning.terningserver.repository.user.UserRepository;
import org.terning.terningserver.dto.user.response.ProfileResponseDto;

import static org.terning.terningserver.exception.enums.ErrorMessage.FAILED_WITHDRAW;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void deleteUser(User user) {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new CustomException(FAILED_WITHDRAW);
        }
    }

    @Override
    public ProfileResponseDto getProfile(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION)
        );

        return ProfileResponseDto.of(user);
    }

    @Override
    @Transactional
    public void updateProfile(Long userId, ProfileUpdateRequestDto request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION));

        try{
            // 프로필 이미지가 유효하지 않으면 IllegalArgumentException을 던짐
            ProfileImage profileImage = ProfileImage.fromValue(request.profileImage());

            //프로필 업데이트
            user.updateProfile(request.name(), ProfileImage.fromValue(request.profileImage()));

            userRepository.save(user);
        } catch (IllegalArgumentException e){
            // 잘못된 프로필 이미지 값이 오면 CustomException 발생
            throw new CustomException(ErrorMessage.INVALID_PROFILE_IMAGE);
        }
    }
}
