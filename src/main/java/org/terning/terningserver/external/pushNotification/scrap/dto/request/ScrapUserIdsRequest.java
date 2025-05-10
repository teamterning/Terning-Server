package org.terning.terningserver.external.pushNotification.scrap.dto.request;

import java.util.List;

public record ScrapUserIdsRequest(List<Long> userIds) {
    public static ScrapUserIdsRequest of(List<Long> userIds) {
        return new ScrapUserIdsRequest(userIds);
    }
}

