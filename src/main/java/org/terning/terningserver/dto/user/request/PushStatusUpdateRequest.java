package org.terning.terningserver.dto.user.request;

public record PushStatusUpdateRequest(String newStatus) {
    public static PushStatusUpdateRequest of(String newStatus) {
        return new PushStatusUpdateRequest(newStatus);
    }
}