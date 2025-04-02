package org.terning.terningserver.external.scrap.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.terning.terningserver.external.scrap.application.port.ScrapSyncNotifier;
import org.terning.terningserver.external.scrap.common.failure.ScrapExternalErrorCode;
import org.terning.terningserver.external.scrap.common.failure.ScrapExternalException;
import org.terning.terningserver.external.scrap.dto.request.ScrapUserIdsRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpsApiClient implements ScrapSyncNotifier {

    private final WebClient opsWebClient;

    @Override
    public void sendScrapSyncResult(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            throw new ScrapExternalException(ScrapExternalErrorCode.EMPTY_USER_IDS);
        }

        ScrapUserIdsRequest request = ScrapUserIdsRequest.of(userIds);
        try {
            opsWebClient.post()
                    .uri("/api/v1/external/scraps/sync/result")
                    .bodyValue(request)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
            throw new ScrapExternalException(ScrapExternalErrorCode.FAILED_TO_SEND_NOTIFICATION);
        }
    }
}
