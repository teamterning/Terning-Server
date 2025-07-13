package org.terning.terningserver.user.event;

import org.terning.terningserver.user.domain.User;

public record UserSignedUpEvent(User user, String fcmToken) {

    public static UserSignedUpEvent of(User user, String fcmToken) {
        return new UserSignedUpEvent(user, fcmToken);
    }
}
