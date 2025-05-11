package org.terning.terningserver.filter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.terning.terningserver.filter.domain.Filter;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {
}
