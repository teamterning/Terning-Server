package org.terning.terningserver.repository.Banner;

import org.springframework.data.repository.Repository;
import org.terning.terningserver.domain.Banner;

import java.util.List;

public interface BannerRepository extends Repository<Banner, Long> {

    List<Banner> findAllByOrderByPriority();
}
