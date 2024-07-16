package org.terning.terningserver.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.AuthType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAuthTypeAndAuthAccessToken(AuthType authType, String authId);

    Optional<User> findByRefreshToken(String refreshToken);

}
