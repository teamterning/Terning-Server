package org.terning.terningserver.banner.application;

import org.springframework.stereotype.Service;
import org.terning.terningserver.banner.domain.Banner;
import org.terning.terningserver.banner.dto.response.BannerListResponseDto;
import org.terning.terningserver.banner.repository.BannerRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    public BannerListResponseDto getBanners() {
        List<Banner> banners = bannerRepository.findAllByOrderByPriority();
        return BannerListResponseDto.of(banners);
    }
}
