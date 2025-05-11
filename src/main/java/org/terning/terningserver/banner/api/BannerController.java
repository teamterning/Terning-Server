package org.terning.terningserver.banner.api;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.terning.terningserver.banner.dto.response.BannerListResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.common.exception.enums.SuccessMessage;
import org.terning.terningserver.banner.application.BannerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BannerController implements BannerSwagger{

    private final BannerService bannerService;

    @GetMapping("/search/banners")
    public ResponseEntity<SuccessResponse<BannerListResponseDto>> getBanners() {
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessMessage.SUCCESS_GET_BANNERS, bannerService.getBanners())
        );
    }
}
