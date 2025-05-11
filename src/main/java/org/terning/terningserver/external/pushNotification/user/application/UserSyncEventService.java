package org.terning.terningserver.external.pushNotification.user.application;


public interface UserSyncEventService {

    void recordNameChange(Long userId, String newName);
    void recordWithdraw(Long userId);
}
