package org.terning.terningserver.filter.api;

import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_USER_FILTER;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_UPDATE_USER_FILTER;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.terning.terningserver.filter.dto.request.UpdateUserFilterRequestDto;
import org.terning.terningserver.filter.dto.response.UserFilterDetailResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.filter.application.FilterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FilterController implements FilterSwagger {

    private final FilterService filterService;

    @GetMapping("/filters")
    public ResponseEntity<SuccessResponse<UserFilterDetailResponseDto>> getUserFilter(
            @AuthenticationPrincipal long userId
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_USER_FILTER,
                filterService.getUserFilter(userId)
        ));
    }

    @PutMapping("/filters")
    public ResponseEntity<SuccessResponse> updateUserFilter(
            @AuthenticationPrincipal long userId,
            @RequestBody UpdateUserFilterRequestDto requestDto
    ) {
        filterService.updateUserFilter(requestDto, userId);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_USER_FILTER));
    }

}
