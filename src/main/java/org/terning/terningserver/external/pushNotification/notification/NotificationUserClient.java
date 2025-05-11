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

    /**
     * 알림서버에 신규 사용자 정보를 전달하여 사용자 레코드를 생성합니다.
     * 운영서버에서는 FCM 토큰을 DB에 저장하지 않고,
     * 기본값으로 pushStatus="enabled"와 accountStatus="active"를 전달합니다.
     *
     * @param userId    신규 사용자 ID
     * @param name      사용자 이름
     * @param authType  인증 타입 (문자열, 예: "kakao", "apple")
     * @param fcmToken  클라이언트로부터 받은 FCM 토큰
     */
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

    /**
     * 소셜로그인 시 알림서버와 운영서버간 유저 동기화를 진행합니다.
     * @param user      유저 객체
     * @param fcmToken  fcm 토큰
     */
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

    /**
     * 알림서버에 새로운 fcm 토큰을 전달합니다.
     *
     * @param userId    사용자 ID
     * @param newToken  새로운 fcm 토큰
     */
    public void updateFcmToken(Long userId, String newToken) {
        notificationWebClient.put()
                .uri("/api/v1/users/{userId}/fcm-tokens", userId)
                .body(Mono.just(newToken), String.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.info("FCM tokens updated for user (id={}): {}", userId, newToken);
    }

    /**
     * 알림서버에 신규 사용자 정보를 전달하여 사용자 레코드를 생성합니다.
     * 운영서버에서는 pushStatus 값을 DB에 저장하지 않고,
     * pushStatus="enabled" 또는 "disabled 로 변경합니다.
     *
     * @param userId         기존 사용자 ID
     * @param newPushStatus  새로운 푸시알림 허용 여부
     */
    public void updatePushStatus(Long userId, String newPushStatus) {
        notificationWebClient.put()
                .uri("/api/v1/users/{userId}/push-status", userId)
                .body(Mono.just(newPushStatus), String.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.info("Push status updated for user (id={}): {}", userId, newPushStatus);
    }

    /**
     * 프로필 정보를 변경하면, 알림서버에 새로운 사용자 이름을 전달하여 사용자 레코드를 변경합니다.
     * 운영서버와 알림서버 모두 변경 값을 반영하도록 합니다.
     *
     * @param userId    기존 사용자 ID
     * @param newName   변경된 새로운 사용자 이름
     */
    public void updateUserName(Long userId, String newName) {
        notificationWebClient.put()
                .uri("/api/v1/users/{userId}/name", userId)
                .body(Mono.just(newName), String.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.info("User name updated for user (id={}): {}", userId, newName);
    }

    /**
     * 회원 탈퇴 시 알림서버에 존재하는 사용자를 삭제하여 사용자 레코드를 변경합니다.
     * 운영서버와 알림서버 모두 해당 유저를 삭제하도록 합니다.
     *
     * @param userId    기존 사용자 ID
     */
    public void deleteUser(Long userId) {
        notificationWebClient.delete()
                .uri("/api/v1/users/{userId}", userId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        log.info("User (id={}) deleted on notification server.", userId);
    }
}
