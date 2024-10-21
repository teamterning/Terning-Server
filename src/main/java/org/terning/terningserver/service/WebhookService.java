package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.repository.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class WebhookService {

    // 디스코드 웹훅 URL 주입
    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    private final UserRepository userRepository;

    // 알림을 보내는 메서드
    public void sendDiscordNotification(User user) {

        // discord.webhook.url 값이 비어있으면 웹훅을 실행하지 않음
        if (discordWebhookUrl == null || discordWebhookUrl.isEmpty()) {
            // 스테이징 환경에서는 웹훅을 비활성화
            return;
        }

        // REST 요청을 처리하기 위한 RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // 회원 수를 기존 DB에서 조회하여 총 회원 수 계산
        Long totalMembers = userRepository.count();

        // 알림 메시지 생성
        String message = String.format("가입자명 : %s\n로그인방식: %s\n[%d] 번째 유저가 회원가입했습니다!", user.getName(), user.getAuthType(), totalMembers);

        // HTTP 요청을 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 바디에 전송할 메시지 설정
        Map<String, String> body = new HashMap<>();
        body.put("content", message);

        // HTTP 요청 엔터티 생성
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // 디스코드 웹훅 URL로 POST 요청을 보내어 알림 전송
        restTemplate.postForEntity(discordWebhookUrl, requestEntity, String.class);
    }
}
