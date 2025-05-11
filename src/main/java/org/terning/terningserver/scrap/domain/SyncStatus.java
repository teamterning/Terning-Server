package org.terning.terningserver.scrap.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class SyncStatus {

    private boolean value;

    protected SyncStatus() {
    }

    private SyncStatus(boolean value) {
        this.value = value;
    }

    public static SyncStatus notSynced() {
        return new SyncStatus(false);
    }

    public static SyncStatus synced() {
        return new SyncStatus(true);
    }

    public boolean isSynced() {
        return value;
    }

    public boolean isNotSynced() {
        return !value;
    }
}

