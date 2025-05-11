package org.terning.terningserver.external.pushNotification.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_sync_events")
public class UserSyncEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated
    private UserSyncEventType eventType;

    private String newValue;

    private LocalDateTime createdAt;

    private boolean processed;

    @Builder
    public UserSyncEvent(Long userId, UserSyncEventType eventType, String newValue, LocalDateTime createdAt) {
        this.userId = userId;
        this.eventType = eventType;
        this.newValue = newValue;
        this.createdAt = createdAt;
        this.processed = false;
    }

    public void markProcessed() {
        this.processed = true;
    }
}
