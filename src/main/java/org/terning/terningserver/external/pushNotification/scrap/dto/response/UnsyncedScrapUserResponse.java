package org.terning.terningserver.external.pushNotification.scrap.dto.response;

public record UnsyncedScrapUserResponse(Long userId) {
    public static UnsyncedScrapUserResponse from(Long userId) {
        return new UnsyncedScrapUserResponse(userId);
    }
}
