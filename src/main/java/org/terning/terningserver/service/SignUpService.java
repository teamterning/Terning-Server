package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.SIGN_UP_FAILED;


@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;

    public User signUp(Long userId, String name, Integer profileImage) {
        return userRepository.findById(userId).map(user -> {
            user.updateProfile(name, profileImage);
            return userRepository.save(user);
        }).orElseThrow(() -> new CustomException(SIGN_UP_FAILED));
    }
}
