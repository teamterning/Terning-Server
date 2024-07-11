package org.terning.terningserver.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.terning.terningserver.domain.Scrap;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
