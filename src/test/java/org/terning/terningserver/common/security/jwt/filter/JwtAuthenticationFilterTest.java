package org.terning.terningserver.common.security.jwt.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.terning.terningserver.common.security.jwt.exception.JwtErrorCode;
import org.terning.terningserver.common.security.jwt.exception.JwtException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JwtAuthenticationFilterTest {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilterTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenVerifier jwtTokenVerifier;

    @Test
    @DisplayName("잘못된 JWT 토큰으로 반복 요청 시, Rate Limit에 따라 429 에러를 반환한다")
    void when_repeated_requests_with_invalid_token_then_return_429_error() throws Exception {
        // given
        String invalidToken = "this-is-an-invalid-token";
        when(jwtTokenVerifier.validateAndExtractUserId(anyString()))
                .thenThrow(new JwtException(JwtErrorCode.INVALID_JWT_TOKEN));

        // when & then
        log.info("====== Rate Limit 허용 횟수 내 요청 테스트 시작 ======");
        for (int i = 1; i <= 10; i++) {
            log.info("{}번째 요청", i);
            // when
            ResultActions actions = mockMvc.perform(get("/api/v1/any-secured-endpoint")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken));
            // then
            actions.andExpect(status().isUnauthorized());
        }
        log.info("====== Rate Limit 허용 횟수 내 요청 테스트 통과 ======");


        // when & then
        log.info("\n====== Rate Limit 초과 요청 테스트 시작 ======");
        log.info("11번째 요청");
        // when
        ResultActions finalAction = mockMvc.perform(get("/api/v1/any-secured-endpoint")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken));
        // then
        finalAction.andExpect(status().isTooManyRequests());
        log.info("====== Rate Limit 초과 요청 테스트 통과 ======");
    }
}
