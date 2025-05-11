package org.terning.terningserver.external.pushNotification.scrap.dto.response;

import java.util.List;

public record UnsyncedScrapUsersResponse(List<UnsyncedScrapUserResponse> users) {

    public static UnsyncedScrapUsersResponse of(List<Long> userIds) {
        List<UnsyncedScrapUserResponse> responses = userIds.stream()
                .map(UnsyncedScrapUserResponse::from)
                .toList();

        return new UnsyncedScrapUsersResponse(responses);
    }
}
