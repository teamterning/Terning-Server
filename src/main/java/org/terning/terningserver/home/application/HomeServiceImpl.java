package org.terning.terningserver.home.application;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.filter.domain.Filter;
import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;
import org.terning.terningserver.scrap.domain.QScrap;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.scrap.domain.Color;
import org.terning.terningserver.filter.domain.JobType;
import org.terning.terningserver.home.dto.response.HomeAnnouncementsResponseDto;
import org.terning.terningserver.home.dto.response.HomeResponseDto;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.common.exception.enums.ErrorMessage;
import org.terning.terningserver.internshipAnnouncement.repository.InternshipRepository;
import org.terning.terningserver.user.repository.UserRepository;

import java.util.List;

import static org.terning.terningserver.internshipAnnouncement.domain.QInternshipAnnouncement.internshipAnnouncement;
import static org.terning.terningserver.scrap.domain.QScrap.scrap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService {

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;

    @Override
    public HomeAnnouncementsResponseDto getAnnouncements(Long userId, String sortBy, Pageable pageable) {
        User user = getUserById(userId);
        Filter filter = user.getFilter();
        Page<Tuple> pagedAnnouncements;
        if (filter == null || isDefaultFilter(filter)) {
            pagedAnnouncements = internshipRepository.findAllInternshipsWithScrapInfo(user, sortBy, pageable);
        } else {
            pagedAnnouncements = internshipRepository.findFilteredInternshipsWithScrapInfo(user, sortBy, pageable);
        }

        if (pagedAnnouncements.isEmpty()) {
            return createEmptyResponse();
        }

        List<HomeResponseDto> responseDtos = mapToHomeResponseDtos(pagedAnnouncements);
        return createResponse(pagedAnnouncements, responseDtos);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION));
    }

    private boolean isDefaultFilter(Filter filter) {
        return filter.getJobType() == JobType.TOTAL
                && filter.getGrade() == null
                && filter.getWorkingPeriod() == null
                && filter.getStartYear() == 0
                && filter.getStartMonth() == 0;
    }

    private HomeAnnouncementsResponseDto createEmptyResponse() {
        return HomeAnnouncementsResponseDto.of(0, 0, false, List.of());
    }

    private List<HomeResponseDto> mapToHomeResponseDtos(Page<Tuple> pagedAnnouncements) {
        return pagedAnnouncements.getContent().stream()
                .map(tuple -> {
                    InternshipAnnouncement announcement = tuple.get(internshipAnnouncement);
                    Long scrapId = tuple.get(scrap.id);
                    Color color = tuple.get(scrap.color);

                    boolean isScrapped = isScrapped(scrapId);
                    String colorValue = determineColorValue(isScrapped, color);

                    return HomeResponseDto.of(announcement, isScrapped, colorValue);
                })
                .toList();
    }

    private boolean isScrapped(Long scrapId) {
        return scrapId != null;
    }

    private String determineColorValue(boolean isScrapped, Color color) {
        if (isScrapped && color != null) {
            return color.getColorValue();
        }
        return null;
    }

    private HomeAnnouncementsResponseDto createResponse(Page<Tuple> pagedAnnouncements, List<HomeResponseDto> responseDtos) {
        return HomeAnnouncementsResponseDto.of(
                pagedAnnouncements.getTotalPages(),
                pagedAnnouncements.getTotalElements(),
                pagedAnnouncements.hasNext(),
                responseDtos
        );
    }
}