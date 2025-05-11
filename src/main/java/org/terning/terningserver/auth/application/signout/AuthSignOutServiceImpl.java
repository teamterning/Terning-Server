package org.terning.terningserver.auth.application.signout;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.user.repository.UserRepository;

import static org.terning.terningserver.common.exception.enums.ErrorMessage.INVALID_USER;

@Service
@RequiredArgsConstructor
public class AuthSignOutServiceImpl implements AuthSignOutService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void signOut(long userId) {
        val user = findUserById(userId);
        user.resetRefreshToken();
    }

    private User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(INVALID_USER));
    }
}
