package org.terning.terningserver.jwt.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.terning.terningserver.domain.User;

public class AuthenticationTokenFactory {
    public static UserAuthentication create(User user) {
        UserDetails userDetails = UserDetailsFactory.create(user.getId());
        return UserAuthentication.authenticated(userDetails);
    }
}