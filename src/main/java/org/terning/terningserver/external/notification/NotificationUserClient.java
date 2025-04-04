package org.terning.terningserver.external.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.external.dto.request.CreateUserRequest;
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

}
