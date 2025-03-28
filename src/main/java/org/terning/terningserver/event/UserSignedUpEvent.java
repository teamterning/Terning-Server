package org.terning.terningserver.event;

import lombok.Getter;
import org.terning.terningserver.domain.User;

@Getter
public class UserSignedUpEvent {
    private final User user;

    private UserSignedUpEvent(User user) {
        this.user = user;
    }

    public static UserSignedUpEvent of(User user) {
        return new UserSignedUpEvent(user);
    }
}
