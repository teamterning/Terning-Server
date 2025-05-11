package org.terning.terningserver.auth.application.social.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.terning.terningserver.common.config.ValueConfig;
import org.terning.terningserver.common.exception.CustomException;

import java.util.Map;

import static org.terning.terningserver.common.exception.enums.ErrorMessage.FAILED_SOCIAL_LOGIN;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoAuthTokenValidator {

    private final ValueConfig valueConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public String extractKakaoId(String authAccessToken) {
        try {
            val headers = new HttpHeaders();
            headers.add("Authorization", authAccessToken);
            val httpEntity = new HttpEntity<JsonArray>(headers);
            val responseData = restTemplate.postForEntity(valueConfig.getKakaoUri(), httpEntity, Object.class);
            return objectMapper.convertValue(responseData.getBody(), Map.class).get("id").toString();
        } catch (Exception exception) {
            throw new CustomException(FAILED_SOCIAL_LOGIN);
        }
    }
}
