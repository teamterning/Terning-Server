package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.dto.user.response.HomeAnnouncementsResponseDto;
import org.terning.terningserver.dto.user.response.HomeResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.exception.enums.ErrorMessage;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;
import org.terning.terningserver.repository.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService{

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public HomeAnnouncementsResponseDto getAnnouncements(Long userId, String sortBy, int startYear, int startMonth){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION)
        );

        // 필터링 상태가 없을 경우 NULL 리턴
        if(user.getFilter() == null){
            return HomeAnnouncementsResponseDto.of(0,List.of());
        }

        List<InternshipAnnouncement> announcements = internshipRepository.findFilteredInternships(user, sortBy, startYear, startMonth);

        List<HomeResponseDto> responseDtos = announcements.stream()
                .map(announcement -> {
                    boolean isScrapped = scrapRepository.existsByInternshipAnnouncementIdAndUserId(announcement.getId(), userId);
                    String color = scrapRepository.findColorByInternshipAnnouncementIdAndUserId(announcement.getId(), userId);
                    return HomeResponseDto.of(announcement, isScrapped, color);
                })
                .toList();

        return HomeAnnouncementsResponseDto.of(responseDtos.size(), responseDtos);
    }
}
