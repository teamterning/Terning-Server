package org.terning.terningserver.repository.filter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.terning.terningserver.domain.Filter;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {
}
