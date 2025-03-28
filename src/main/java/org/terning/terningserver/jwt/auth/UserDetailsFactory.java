package org.terning.terningserver.jwt.auth;

import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsFactory {
    public static UserDetails create(Long userId) {
        return new CustomUserDetails(userId);
    }
}
