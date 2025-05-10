package org.terning.terningserver.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.terning.terningserver.controller.swagger.InternshipDetailSwagger;
import org.terning.terningserver.dto.internship_detail.InternshipDetailResponseDto;
import org.terning.terningserver.common.exception.dto.SuccessResponse;
import org.terning.terningserver.service.InternshipDetailService;

import static org.terning.terningserver.common.exception.enums.SuccessMessage.SUCCESS_GET_INTERNSHIP_DETAIL;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class InternshipDetailController implements InternshipDetailSwagger {

    private final InternshipDetailService internshipDetailService;

    @GetMapping("/announcements/{internshipAnnouncementId}")
    public ResponseEntity<SuccessResponse<InternshipDetailResponseDto>> getInternshipDetail(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long internshipAnnouncementId) {
        return ResponseEntity.ok(SuccessResponse.of(
                SUCCESS_GET_INTERNSHIP_DETAIL,
                internshipDetailService.getInternshipDetail(internshipAnnouncementId, userId)
        ));
    }


}

