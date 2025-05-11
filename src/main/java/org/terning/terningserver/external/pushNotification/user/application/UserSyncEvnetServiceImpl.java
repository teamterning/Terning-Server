package org.terning.terningserver.external.pushNotification.user.application;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.external.pushNotification.user.domain.UserSyncEvent;
import org.terning.terningserver.external.pushNotification.user.domain.UserSyncEventType;
import org.terning.terningserver.external.pushNotification.user.respository.UserSyncEventRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSyncEvnetServiceImpl implements UserSyncEventService {

    private final UserSyncEventRepository eventRepository;

    @Transactional
    public void recordPushStatusChange(Long userId, String newPushStatus) {
        UserSyncEvent event = UserSyncEvent.builder()
                .userId(userId)
                .eventType(UserSyncEventType.PUSH_STATUS_CHANGE)
                .newValue(newPushStatus)
                .createdAt(LocalDateTime.now())
                .build();
        eventRepository.save(event);
    }

    @Transactional
    public void recordNameChange(Long userId, String newName) {
        UserSyncEvent event = UserSyncEvent.builder()
                .userId(userId)
                .eventType(UserSyncEventType.NAME_CHANGE)
                .newValue(newName)
                .createdAt(LocalDateTime.now())
                .build();
        eventRepository.save(event);
    }

    @Transactional
    public void recordWithdraw(Long userId) {
        UserSyncEvent event = UserSyncEvent.builder()
                .userId(userId)
                .eventType(UserSyncEventType.WITHDRAW)
                .newValue(null)
                .createdAt(LocalDateTime.now())
                .build();
        eventRepository.save(event);
    }
}
