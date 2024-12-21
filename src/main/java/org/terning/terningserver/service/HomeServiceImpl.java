package org.terning.terningserver.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.Color;
import org.terning.terningserver.dto.user.response.HomeAnnouncementsResponseDto;
import org.terning.terningserver.dto.user.response.HomeResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.exception.enums.ErrorMessage;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.user.UserRepository;

import java.util.List;

import static org.terning.terningserver.domain.QInternshipAnnouncement.internshipAnnouncement;
import static org.terning.terningserver.domain.QScrap.scrap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService {

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;

    @Override
    public HomeAnnouncementsResponseDto getAnnouncements(Long userId, String sortBy, Pageable pageable) {
        User user = getUserById(userId);

        if (user.getFilter() == null) {
            return createEmptyResponse();
        }

        Page<Tuple> pagedAnnouncements = internshipRepository.findFilteredInternshipsWithScrapInfo(user, sortBy, pageable);

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