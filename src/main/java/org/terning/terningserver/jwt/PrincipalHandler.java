package org.terning.terningserver.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.terning.terningserver.exception.CustomException;

import static org.terning.terningserver.exception.enums.ErrorMessage.UNAUTHORIZED_JWT_EXCEPTION;

@Component
public class PrincipalHandler {
    private static final String ANONYMOUS_USER = "anonymousUser";

    public Long getUserIdFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        isPrincipalNull(principal);
        return Long.valueOf(principal.toString());
    }

    public void isPrincipalNull(
            final Object principal
    ) {
        if (principal.toString().equals(ANONYMOUS_USER)) {
            throw new CustomException(UNAUTHORIZED_JWT_EXCEPTION);
        }
    }
}