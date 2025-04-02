package org.terning.terningserver.external.scrap.application.port;

import java.util.List;

public interface ScrapSyncNotifier {
    void notify(List<Long> userIds);
}
