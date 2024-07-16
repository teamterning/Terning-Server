package org.terning.terningserver.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.AuthType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAuthTypeAndRefreshToken(AuthType authType, String refreshToken);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findByAuthId(String authId);

    Optional<User> findByAuthIdAndAuthType(String authId, AuthType authType);

}
