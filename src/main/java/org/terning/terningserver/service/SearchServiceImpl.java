package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.dto.search.response.PopularAnnouncementListResponseDto;
import org.terning.terningserver.dto.search.response.SearchResultResponseDto;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {

    private final InternshipRepository internshipRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public PopularAnnouncementListResponseDto getMostViewedAnnouncements() {
        List<InternshipAnnouncement> mostViewedInternships = internshipRepository.getMostViewedInternship();
        return PopularAnnouncementListResponseDto.of(mostViewedInternships);
    }

    @Override
    public PopularAnnouncementListResponseDto getMostScrappedAnnouncements() {
        List<InternshipAnnouncement> mostViewedInternships = internshipRepository.getMostScrappedInternship();
        return PopularAnnouncementListResponseDto.of(mostViewedInternships);
    }

    @Override
    public SearchResultResponseDto searchInternshipAnnouncement(String keyword, String sortBy, Pageable pageable) {
        Page<InternshipAnnouncement> pageAnnouncements = internshipRepository.searchInternshipAnnouncement(keyword, sortBy, pageable);

        List<InternshipAnnouncement> announcements = pageAnnouncements.getContent();

        List<SearchResultResponseDto.SearchAnnouncementResponse> searchAnnouncementResponses = new ArrayList<>();

        List<Scrap> scraps = scrapRepository.findAllByInternshipAndUserId(announcements, 1L);

        //스크랩 정보를 매핑 (인턴 공고 ID -> 스크랩 ID)
        Map<Long, Long> scrapMap = scraps.stream()
                .collect(Collectors.toMap(
                        scrap -> scrap.getInternshipAnnouncement().getId(),
                        Scrap::getId
                ));

        return new SearchResultResponseDto(
                pageAnnouncements.getTotalPages(), pageAnnouncements.hasNext(), announcements.stream()
                .map(a -> SearchResultResponseDto.SearchAnnouncementResponse.from(a, scrapMap.get(a.getId())))
                .toList());
    }
}