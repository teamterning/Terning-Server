package org.terning.terningserver.common.security.jwt.auth;

import org.terning.terningserver.domain.User;

public class AuthenticationTokenFactory {
    public static UserAuthentication create(User user) {
        Long userId = user.getId();
        return new UserAuthentication(userId);
    }
}
