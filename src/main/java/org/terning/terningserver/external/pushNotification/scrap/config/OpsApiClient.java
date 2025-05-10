package org.terning.terningserver.external.pushNotification.scrap.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.terning.terningserver.external.pushNotification.scrap.application.port.ScrapSyncNotifier;
import org.terning.terningserver.external.pushNotification.scrap.common.failure.ScrapExternalErrorCode;
import org.terning.terningserver.external.pushNotification.scrap.common.failure.ScrapExternalException;
import org.terning.terningserver.external.pushNotification.scrap.dto.request.ScrapUserIdsRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpsApiClient implements ScrapSyncNotifier {

    private final WebClient operationBaseUrlWebClient;

    @Override
    public void notify(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            throw new ScrapExternalException(ScrapExternalErrorCode.EMPTY_USER_IDS);
        }

        ScrapUserIdsRequest request = ScrapUserIdsRequest.of(userIds);
        try {
            operationBaseUrlWebClient.post()
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
