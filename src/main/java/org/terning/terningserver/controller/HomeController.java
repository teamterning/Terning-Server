package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.HomeSwagger;
import org.terning.terningserver.dto.user.response.HomeResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.exception.enums.SuccessMessage;
import org.terning.terningserver.service.HomeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController implements HomeSwagger {

    private final HomeService homeService;

    @GetMapping("/home")
    public ResponseEntity<SuccessResponse<List<HomeResponseDto>>> getAnnouncements(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "sortBy", required = false, defaultValue = "deadlineSoon") String sortBy,
            @RequestParam("startYear") int startYear,
            @RequestParam("startMonth") int startMonth
    ){
        List<HomeResponseDto> announcements = homeService.getAnnouncements(token, sortBy, startYear, startMonth);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.SUCCESS_GET_ANNOUNCEMENTS, announcements));
    }
}
