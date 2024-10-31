package org.terning.terningserver.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.dto.Banner.BannerListResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.exception.enums.SuccessMessage;
import org.terning.terningserver.service.BannerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/search/banners")
    public ResponseEntity<SuccessResponse<BannerListResponseDto>> getBanners() {
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessMessage.SUCCESS_GET_BANNERS, bannerService.getBanners())
        );
    }
}
