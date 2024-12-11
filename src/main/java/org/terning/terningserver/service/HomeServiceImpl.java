package org.terning.terningserver.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.User;
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
public class HomeServiceImpl implements HomeService{

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;

    @Override
    public HomeAnnouncementsResponseDto getAnnouncements(Long userId, String sortBy, int startYear, int startMonth){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION)
        );

        if(user.getFilter() == null){
            return HomeAnnouncementsResponseDto.of(0,List.of());
        }

        List<Tuple> results = internshipRepository.findFilteredInternshipsWithScrapInfo(user, sortBy, startYear, startMonth);

        List<HomeResponseDto> responseDtos = results.stream()
                .map(tuple -> {
                    InternshipAnnouncement announcement = tuple.get(internshipAnnouncement);
                    Long scrapId = tuple.get(scrap.id);
                    String color = tuple.get(scrap.color.stringValue());

                    boolean isScrapped = isScrapped(scrapId);
                    return HomeResponseDto.of(announcement, isScrapped, color);
                })
                .toList();

        return HomeAnnouncementsResponseDto.of(responseDtos.size(), responseDtos);
    }

    private boolean isScrapped(Long scrapId) {
        return scrapId != null;
    }
}
