package org.terning.terningserver.external.pushNotification.scrap.application.port;

import java.util.List;

public interface UnsyncedScrapMarker {
    void markAsSynced(List<Long> userIds);
}
