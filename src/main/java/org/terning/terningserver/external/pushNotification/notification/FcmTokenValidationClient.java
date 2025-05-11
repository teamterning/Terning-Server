package org.terning.terningserver.external.pushNotification.notification;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.terning.terningserver.external.pushNotification.dto.FcmTokenReissueRequiredResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class FcmTokenValidationClient {

    private static final String FCM_VALIDATION_PATH = "/api/v1/users/{userId}/fcm-tokens/reissue-required";
    private static final String FAIL_LOG = "FCM 토큰 유효성 검사 요청 실패";
    private static final String FAIL_LOG_FALLBACK = FAIL_LOG + " (fallback) - userId: {}";

    private final WebClient notificationWebClient;

    @CircuitBreaker(name = "fcmValidation", fallbackMethod = "fallback")
    public boolean requestFcmTokenValidation(Long userId) {
        FcmTokenReissueRequiredResponse response = notificationWebClient.get()
                .uri(FCM_VALIDATION_PATH, userId)
                .retrieve()
                .bodyToMono(FcmTokenReissueRequiredResponse.class)
                .block();

        return response != null && response.reissueRequired();
    }

    public boolean fallback(Long userId, Throwable t) {
        log.warn(FAIL_LOG_FALLBACK, userId, t);
        return false;
    }
}

