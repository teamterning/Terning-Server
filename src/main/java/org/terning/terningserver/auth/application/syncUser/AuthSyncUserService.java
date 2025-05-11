package org.terning.terningserver.auth.application.syncUser;

import org.terning.terningserver.auth.dto.request.FcmTokenSyncRequest;

public interface AuthSyncUserService {

    void syncUser(long userId, FcmTokenSyncRequest request);
}
