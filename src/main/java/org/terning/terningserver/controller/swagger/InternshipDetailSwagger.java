package org.terning.terningserver.controller.swagger;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.terning.terningserver.dto.internship_detail.InternshipDetailResponseDto;
import org.terning.terningserver.exception.dto.SuccessResponse;

@Tag(name = "InternshipDetail", description = "공고 상세 페이지 관련 API")
public interface InternshipDetailSwagger {

    @Operation(summary = "공고 상세 페이지", description = "인턴 공고의 상세 정보를 불러오는 API")
    ResponseEntity<SuccessResponse<InternshipDetailResponseDto>> getInternshipDetail(
            @PathVariable("internshipAnnouncementId") Long internshipAnnouncementId
    );
}
