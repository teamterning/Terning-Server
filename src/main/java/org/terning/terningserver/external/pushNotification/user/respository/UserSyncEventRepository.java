package org.terning.terningserver.external.pushNotification.user.respository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.external.pushNotification.user.domain.UserSyncEvent;

public interface UserSyncEventRepository extends JpaRepository<UserSyncEvent, Long> {
    List<UserSyncEvent> findByProcessedFalse();
}
