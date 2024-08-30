package org.terning.terningserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.terning.terningserver.config.ValueConfig;
import org.terning.terningserver.exception.CustomException;

import java.util.Map;

import static org.terning.terningserver.exception.enums.ErrorMessage.FAILED_SOCIAL_LOGIN;


@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private final ValueConfig valueConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public String getKakaoData(String authAccessToken) {
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
