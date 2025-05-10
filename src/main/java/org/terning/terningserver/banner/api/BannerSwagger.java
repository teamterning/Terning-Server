package org.terning.terningserver.banner.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;

import org.terning.terningserver.banner.dto.response.BannerListResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;

@Tag(name= "Banner", description = "탐색 > 배너 조회 관련 API")
public interface BannerSwagger {

    @Operation(summary = "배너 조회", description = "탐색 > 배너를 조회하는 API")
    ResponseEntity<SuccessResponse<BannerListResponseDto>> getBanners();

}
