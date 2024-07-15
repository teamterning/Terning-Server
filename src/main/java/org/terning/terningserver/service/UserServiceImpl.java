package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.WITHDRAW_FAILED;

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
            throw new CustomException(WITHDRAW_FAILED);
        }
    }

}
