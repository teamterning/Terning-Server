package org.terning.terningserver.dto.user.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HomeAnnouncementsResponseDto(
        int totalCount, // 필터링 된 공고 총 개수
        List<HomeResponseDto> result
) {
    public static HomeAnnouncementsResponseDto of(final int totalCount, final List<HomeResponseDto> announcements){
        return HomeAnnouncementsResponseDto.builder()
                .totalCount(totalCount)
                .result(announcements)
                .build();
    }
}
