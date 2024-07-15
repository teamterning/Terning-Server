package org.terning.terningserver.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
}
