package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.FilterSwagger;
import org.terning.terningserver.dto.filter.request.UserFilterRequestDto;
import org.terning.terningserver.dto.filter.response.UserFilterResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.service.FilterService;

import static org.terning.terningserver.exception.enums.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FilterController implements FilterSwagger {

    private final FilterService filterService;

    @GetMapping("/filters")
    public ResponseEntity<SuccessResponse<UserFilterResponseDto>> getUserFilter(
            @AuthenticationPrincipal Long userId
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_USER_FILTER,
                filterService.getUserFilter(userId)
        ));
    }

    @PutMapping("/filters")
    public ResponseEntity<SuccessResponse> updateUserFilter(
            @AuthenticationPrincipal Long userId,
            @RequestBody UserFilterRequestDto requestDto) {
        filterService.updateUserFilter(requestDto, userId);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_USER_FILTER));
    }

}