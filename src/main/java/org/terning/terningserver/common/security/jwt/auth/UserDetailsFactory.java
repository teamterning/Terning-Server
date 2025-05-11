package org.terning.terningserver.common.security.jwt.auth;

import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsFactory {
    public static UserDetails create(Long userId) {
        return new CustomUserDetails(userId);
    }
}
