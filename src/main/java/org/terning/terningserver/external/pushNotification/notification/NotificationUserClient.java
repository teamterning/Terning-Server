package org.terning.terningserver.external.pushNotification.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.user.domain.AuthType;
import org.terning.terningserver.external.pushNotification.dto.request.CreateUserRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationUserClient {

    private final WebClient notificationWebClient;

    public void createUserOnNotificationServer(
            Long userId,
            String name,
            AuthType authType,
            String fcmToken
    ) {
        CreateUserRequest request = new CreateUserRequest(
                userId,
                name,
                authType,
                fcmToken,
                "enabled",
                "active"
        );

        notificationWebClient.post()
                .uri("/api/v1/users")
                .body(Mono.just(request), CreateUserRequest.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.info("User (id={}) created on notification server : ", userId);
    }

    public void createOrUpdateUser(User user, String fcmToken) {
        try {
            updateFcmToken(user.getId(), fcmToken);
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                createUserOnNotificationServer(
                        user.getId(),
                        user.getName(),
                        user.getAuthType(),
                        fcmToken
                );
            } else {
                throw e;
            }
        }
    }

    public void updateFcmToken(Long userId, String newToken) {
        notificationWebClient.put()
                .uri("/api/v1/users/{userId}/fcm-tokens", userId)
                .body(Mono.just(newToken), String.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.info("FCM tokens updated for user (id={}): {}", userId, newToken);
    }

    public void updatePushStatus(Long userId, String newPushStatus) {
        notificationWebClient.put()
                .uri("/api/v1/users/{userId}/push-status", userId)
                .body(Mono.just(newPushStatus), String.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.info("Push status updated for user (id={}): {}", userId, newPushStatus);
    }

    public void updateUserName(Long userId, String newName) {
        notificationWebClient.put()
                .uri("/api/v1/users/{userId}/name", userId)
                .body(Mono.just(newName), String.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.info("User name updated for user (id={}): {}", userId, newName);
    }

    public void deleteUser(Long userId) {
        notificationWebClient.delete()
                .uri("/api/v1/users/{userId}", userId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.info("User (id={}) deleted on notification server.", userId);
    }
}
