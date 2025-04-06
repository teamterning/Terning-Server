package org.terning.terningserver.external.user.application;


public interface UserSyncEventService {

    void recordNameChange(Long userId, String newName);
    void recordWithdraw(Long userId);
}
