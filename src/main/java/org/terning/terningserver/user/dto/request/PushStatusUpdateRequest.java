package org.terning.terningserver.user.dto.request;

public record PushStatusUpdateRequest(String newStatus) {
    public static PushStatusUpdateRequest of(String newStatus) {
        return new PushStatusUpdateRequest(newStatus);
    }
}