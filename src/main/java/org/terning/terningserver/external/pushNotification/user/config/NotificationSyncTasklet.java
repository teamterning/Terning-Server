package org.terning.terningserver.external.pushNotification.user.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.terning.terningserver.external.pushNotification.notification.NotificationUserClient;
import org.terning.terningserver.external.pushNotification.user.domain.UserSyncEvent;
import org.terning.terningserver.external.pushNotification.user.respository.UserSyncEventRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationSyncTasklet implements Tasklet {

    private final UserSyncEventRepository eventRepository;
    private final NotificationUserClient notificationUserClient;

    private static final String NO_SYNC_EVENTS = "Unsync 상태의 유저 event가 없습니다.";
    private static final String COMPLETED_SYNC_EVENTS = "유저 이벤트 동기화에 성공했습니다.";
    private static final String FAILED_SYNC_EVENTS = "유저 이벤트 동기화에 성공했습니다.";

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<UserSyncEvent> events = eventRepository.findByProcessedFalse();
        if (events.isEmpty()) {
            log.info(NO_SYNC_EVENTS);
            return RepeatStatus.FINISHED;
        }

        for (UserSyncEvent event : events) {
            try {
                Long userId = event.getUserId();
                switch (event.getEventType()) {
                    case PUSH_STATUS_CHANGE:
                        notificationUserClient.updatePushStatus(userId, event.getNewValue());
                        break;
                    case NAME_CHANGE:
                        notificationUserClient.updateUserName(userId, event.getNewValue());
                        break;
                    case WITHDRAW:
                        notificationUserClient.deleteUser(userId);
                        break;
                }
                event.markProcessed();
                eventRepository.save(event);
                log.info(COMPLETED_SYNC_EVENTS + " Id = " + event.getId() + " userId = " + event.getUserId());
            } catch (Exception e) {
                log.error(FAILED_SYNC_EVENTS + " Id = " + event.getId() + " userId = " + event.getUserId());
            }
        }
        return RepeatStatus.FINISHED;
    }
}
