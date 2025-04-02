package org.terning.terningserver.external.scrap.application.port;

import java.util.List;

public interface UnsyncedScrapUserReader {
    List<Long> readUnsyncedUserIds();
}

