package org.terning.terningserver.service;

import org.springframework.stereotype.Service;
import org.terning.terningserver.domain.Banner;
import org.terning.terningserver.dto.Banner.BannerListResponseDto;
import org.terning.terningserver.repository.Banner.BannerRepository;

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
