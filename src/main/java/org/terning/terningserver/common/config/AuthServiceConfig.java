package org.terning.terningserver.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.terning.terningserver.auth.application.social.SocialAuthProvider;
import org.terning.terningserver.auth.application.social.SocialAuthServiceManager;

import java.util.List;

@Configuration
public class AuthServiceConfig {

    @Bean
    public SocialAuthServiceManager socialAuthServiceFactory(List<SocialAuthProvider> socialAuthProviders) {
        return SocialAuthServiceManager.create(socialAuthProviders);
    }
}

