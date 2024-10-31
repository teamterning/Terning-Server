package org.terning.terningserver.dto.Banner;

import org.terning.terningserver.domain.Banner;

import java.util.List;

public record BannerListResponseDto(
        List<BannerDetailResponseDto> banners
) {

    public record BannerDetailResponseDto(
            String imageUrl,
            String link
    ) {
        public static BannerDetailResponseDto of(final Banner banner) {
            return new BannerDetailResponseDto(banner.getImageUrl(), banner.getLink());
        }
    }

    public static BannerListResponseDto of(final List<Banner> banners) {
        return new BannerListResponseDto(
                banners.stream().map(BannerDetailResponseDto::of).toList()
        );
    }
}
