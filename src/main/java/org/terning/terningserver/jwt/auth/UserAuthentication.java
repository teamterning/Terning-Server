package org.terning.terningserver.jwt.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {
    private UserAuthentication(UserDetails principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static UserAuthentication authenticated(UserDetails userDetails) {
        return new UserAuthentication(userDetails, null, userDetails.getAuthorities());
    }

    public static UserAuthentication unauthenticated(UserDetails userDetails, Object credentials) {
        return new UserAuthentication(userDetails, credentials, null);
    }
}

