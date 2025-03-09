package org.terning.terningserver.jwt.auth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collections;

public class UserDetailsFactory {
    public static UserDetails create(Long userId) {
        return new User(userId.toString(), "", Collections.emptyList());
    }
}
