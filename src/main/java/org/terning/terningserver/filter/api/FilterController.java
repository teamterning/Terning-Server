package org.terning.terningserver.filter.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.auth.config.Login;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.filter.application.FilterService;
import org.terning.terningserver.filter.dto.request.UpdateUserFilterRequestDto;
import org.terning.terningserver.filter.dto.response.UserFilterDetailResponseDto;

import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_USER_FILTER;
import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_UPDATE_USER_FILTER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FilterController implements FilterSwagger {

    private final FilterService filterService;

    @GetMapping("/filters")
    public ResponseEntity<SuccessResponse<UserFilterDetailResponseDto>> getUserFilter(
            @Login long userId
    ) {
        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_USER_FILTER,
                filterService.getUserFilter(userId)
        ));
    }

    @PutMapping("/filters")
    public ResponseEntity<SuccessResponse> updateUserFilter(
            @Login long userId,
            @RequestBody UpdateUserFilterRequestDto requestDto
    ) {
        filterService.updateUserFilter(requestDto, userId);
        return ResponseEntity.ok(SuccessResponse.of(SUCCESS_UPDATE_USER_FILTER));
    }
}
