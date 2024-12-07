package org.terning.terningserver.service;

import static org.terning.terningserver.domain.QInternshipAnnouncement.internshipAnnouncement;
import static org.terning.terningserver.domain.QScrap.scrap;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService{

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;

    @Override
    public HomeAnnouncementsResponseDto getAnnouncements(Long userId, String sortBy, int startYear, int startMonth){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION)
        );

        // 유저의 필터 정보가 없는 경우
        if(user.getFilter() == null){
            return HomeAnnouncementsResponseDto.of(0,List.of());
        }

        List<Tuple> announcements = internshipRepository.findFilteredInternshipsWithScrapInfo(user, sortBy, startYear, startMonth);

        // 해당하는 공고가 없는 경우
        if(announcements.isEmpty()){
            return HomeAnnouncementsResponseDto.of(0, List.of());
        }

        List<HomeResponseDto> responseDtos = announcements.stream()
                .map(tuple -> {
                    InternshipAnnouncement announcement = tuple.get(internshipAnnouncement);
                    Long scrapId = tuple.get(scrap.id);
                    Color color = tuple.get(scrap.color);
                    boolean isScrapped = (scrapId != null); // 스크랩 여부

                    // scrap 하지 않은 경우 color는 지정되지 않아야 한다.
                    String colorValue = (isScrapped && color != null) ? color.getColorValue() : null;

                    return HomeResponseDto.of(announcement, isScrapped, colorValue);
                })
                .toList();

        return HomeAnnouncementsResponseDto.of(responseDtos.size(), responseDtos);
    }
}
