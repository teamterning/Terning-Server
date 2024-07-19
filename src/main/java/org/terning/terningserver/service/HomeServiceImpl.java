package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.dto.user.response.HomeResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.exception.enums.ErrorMessage;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;
import org.terning.terningserver.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService{

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public List<HomeResponseDto> getAnnouncements(Long userId, String sortBy, int startYear, int startMonth){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION)
        );

        // 필터링 상태가 없을 경우 NULL 리턴
        if(user.getFilter() == null){
            return List.of();
        }

        List<InternshipAnnouncement> announcements = internshipRepository.findFilteredInternships(user, sortBy, startYear, startMonth);

        return announcements.stream()
                .map(announcement -> {
                    boolean isScrapped = scrapRepository.existsByInternshipAnnouncementIdAndUserId(announcement.getId(), userId);
                    Long scrapId = isScrapped ? scrapRepository.findScrapIdByInternshipAnnouncementIdAndUserId(announcement.getId(), userId) : null;
                    return HomeResponseDto.of(scrapId, announcement, isScrapped);
                })
                .toList();
    }
}
