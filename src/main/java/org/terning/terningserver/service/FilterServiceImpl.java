package org.terning.terningserver.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.domain.Filter;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.Grade;
import org.terning.terningserver.domain.enums.JobType;
import org.terning.terningserver.domain.enums.WorkingPeriod;
import org.terning.terningserver.dto.filter.request.UpdateUserFilterRequestDto;
import org.terning.terningserver.dto.filter.response.UserFilterDetailResponseDto;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.filter.FilterRepository;
import org.terning.terningserver.repository.user.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.NOT_FOUND_USER_EXCEPTION;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilterServiceImpl implements FilterService {

    private final UserRepository userRepository;
    private final FilterRepository filterRepository;

    @Override
    public UserFilterDetailResponseDto getUserFilter(Long userId) {
       return UserFilterDetailResponseDto.of(findUser(userId).getFilter());
    }

    @Override
    @Transactional
    public void updateUserFilter(UpdateUserFilterRequestDto responseDto, Long userId) {
        User user = findUser(userId);
        Filter filter = user.getFilter();

        JobType jobType = (responseDto.jobType() == null || responseDto.jobType().isBlank())
                ? JobType.TOTAL : JobType.fromKey(responseDto.jobType());
        Grade grade = Grade.fromKey(responseDto.grade());
        WorkingPeriod workingPeriod = WorkingPeriod.fromKey(responseDto.workingPeriod());

        if (filter != null) {
            updateExistingFilter(filter, jobType, grade, workingPeriod, responseDto);
        }

        createFilter(user, jobType, grade, workingPeriod, responseDto);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_EXCEPTION));
    }

    private void updateExistingFilter(Filter filter, JobType jobType, Grade grade, WorkingPeriod workingPeriod, UpdateUserFilterRequestDto dto) {
        filter.updateFilter(
                jobType,
                grade,
                workingPeriod,
                dto.startYear(),
                dto.startMonth()
        );
    }

    private void createFilter(User user, JobType jobType, Grade grade, WorkingPeriod workingPeriod, UpdateUserFilterRequestDto dto) {
        Filter savedFilter = filterRepository.save(
                Filter.builder()
                        .jobType(jobType)
                        .grade(grade)
                        .workingPeriod(workingPeriod)
                        .startYear(dto.startYear())
                        .startMonth(dto.startMonth())
                        .build()
        );
        user.assignFilter(savedFilter);
    }
}
