package org.terning.terningserver.auth.application.social;

import org.terning.terningserver.user.domain.AuthType;

public interface SocialAuthProvider {
    String getAuthId(String authAccessToken);
    AuthType supports();
}
