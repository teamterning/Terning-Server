package org.terning.terningserver.auth.application.social;

import org.terning.terningserver.domain.enums.AuthType;

public interface SocialAuthProvider {
    String getAuthId(String authAccessToken);
    AuthType supports();
}
