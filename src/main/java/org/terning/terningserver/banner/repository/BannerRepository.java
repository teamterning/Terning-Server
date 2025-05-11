package org.terning.terningserver.banner.repository;

import org.springframework.data.repository.Repository;
import org.terning.terningserver.banner.domain.Banner;

import java.util.List;

public interface BannerRepository extends Repository<Banner, Long> {

    List<Banner> findAllByOrderByPriority();
}
