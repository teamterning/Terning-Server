package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.user.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.FAILED_SIGN_UP;


@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;

    public User signUp(Long userId, String name, Integer profileImage) {
        return userRepository.findById(userId).map(user -> {
            user.updateProfile(name, profileImage);
            return userRepository.save(user);
        }).orElseThrow(() -> new CustomException(FAILED_SIGN_UP));
    }
}
