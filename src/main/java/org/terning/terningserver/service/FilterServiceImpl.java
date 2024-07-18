package org.terning.terningserver.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.Filter;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.Grade;
import org.terning.terningserver.domain.enums.WorkingPeriod;
import org.terning.terningserver.dto.filter.request.UserFilterRequestDto;
import org.terning.terningserver.dto.filter.response.UserFilterResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.user.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.NOT_FOUND_USER_EXCEPTION;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilterServiceImpl implements FilterService {

    private final UserRepository userRepository;

    @Override
    public UserFilterResponseDto getUserFilter(Long userId) {
       return UserFilterResponseDto.of(findUser(userId).getFilter());
    }

    @Override
    @Transactional
    public void updateUserFilter(UserFilterRequestDto responseDto, Long userId) {
        User user = findUser(userId);
        Filter filter = user.getFilter();
        System.out.println("filter = " + filter);
        if(filter != null){
            filter.updateFilter(
                    Grade.fromKey(responseDto.grade()),
                    WorkingPeriod.fromKey(responseDto.workingPeriod()),
                    responseDto.startYear(),
                    responseDto.startMonth()
            );
        }
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_EXCEPTION));
    }
}
