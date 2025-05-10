package org.terning.terningserver.auth.application.withdraw;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.user.repository.UserRepository;
import org.terning.terningserver.user.application.UserService;

import static org.terning.terningserver.common.exception.enums.ErrorMessage.INVALID_USER;

@Service
@RequiredArgsConstructor
public class AuthWithdrawServiceImpl implements AuthWithdrawService {

    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    @Override
    public void withdraw(long userId) {
        val user = findUserById(userId);

        userService.deleteUser(user);
    }

    private User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(INVALID_USER));
    }
}
