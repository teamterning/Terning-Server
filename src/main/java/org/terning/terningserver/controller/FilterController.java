package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SuccessResponse<UserFilterResponseDto>> getUserFilter(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_USER_FILTER,
                filterService.getUserFilter()
        ));
    }

    @PutMapping("/filters")
    public ResponseEntity<SuccessResponse> updateUserFilter(
            @RequestHeader("Authorization") String token,
            @RequestBody UserFilterRequestDto requestDto) {
        filterService.updateUserFilter(requestDto);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_SCRAP));
    }

}
