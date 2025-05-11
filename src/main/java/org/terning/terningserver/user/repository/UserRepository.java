package org.terning.terningserver.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.user.domain.AuthType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findByAuthId(String authId);

    Optional<User> findByAuthIdAndAuthType(String authId, AuthType authType);
}
