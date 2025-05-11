package org.terning.terningserver.home.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HomeAnnouncementsResponseDto(
        int totalPages,
        long totalCount,
        boolean hasNext,
        List<HomeResponseDto> result
) {
    public static HomeAnnouncementsResponseDto of(
            final int totalPages,
            final long totalCount,
            final boolean hasNext,
            final List<HomeResponseDto> announcements) {
        return HomeAnnouncementsResponseDto.builder()
                .totalPages(totalPages)
                .totalCount(totalCount)
                .hasNext(hasNext)
                .result(announcements)
                .build();
    }
}
