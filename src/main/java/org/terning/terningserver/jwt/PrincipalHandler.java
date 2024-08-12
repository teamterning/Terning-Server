package org.terning.terningserver.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.exception.enums.ErrorMessage;
import org.terning.terningserver.repository.user.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.INVALID_USER;
import static org.terning.terningserver.exception.enums.ErrorMessage.UNAUTHORIZED_JWT_EXCEPTION;

@Component
@RequiredArgsConstructor
public class PrincipalHandler {

    private final UserRepository userRepository;
    private final String ANONYMOUS_USER = "anonymousUser";

    public Long getUserFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByAuthId(principal.toString())
                .orElseThrow(() -> new CustomException(INVALID_USER)).getId();
    }

    public void isPrincipalNull(
            final Object principal
    ) {
        if (principal.toString().equals(ANONYMOUS_USER)) {
            throw new CustomException(UNAUTHORIZED_JWT_EXCEPTION);
        }
    }
}