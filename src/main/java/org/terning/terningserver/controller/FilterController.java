package org.terning.terningserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.controller.swagger.FilterSwagger;
import org.terning.terningserver.dto.filter.request.UserFilterRequestDto;
import org.terning.terningserver.dto.filter.response.UserFilterResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;
import org.terning.terningserver.jwt.PrincipalHandler;
import org.terning.terningserver.service.FilterService;

import static org.terning.terningserver.exception.enums.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FilterController implements FilterSwagger {

    private final FilterService filterService;
    private final PrincipalHandler principalHandler;

    @GetMapping("/filters")
    public ResponseEntity<SuccessResponse<UserFilterResponseDto>> getUserFilter(
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_USER_FILTER,
                filterService.getUserFilter(principalHandler.getUserFromPrincipal())
        ));
    }

    @PutMapping("/filters")
    public ResponseEntity<SuccessResponse> updateUserFilter(@RequestBody UserFilterRequestDto requestDto) {
        filterService.updateUserFilter(requestDto, principalHandler.getUserFromPrincipal());
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_SCRAP));
    }

}
