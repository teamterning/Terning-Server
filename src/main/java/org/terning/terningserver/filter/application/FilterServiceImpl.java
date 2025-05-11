package org.terning.terningserver.filter.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terning.terningserver.filter.domain.Filter;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.filter.domain.Grade;
import org.terning.terningserver.filter.domain.JobType;
import org.terning.terningserver.filter.domain.WorkingPeriod;
import org.terning.terningserver.filter.dto.request.UpdateUserFilterRequestDto;
import org.terning.terningserver.filter.dto.response.UserFilterDetailResponseDto;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.filter.repository.FilterRepository;
import org.terning.terningserver.user.repository.UserRepository;

import static org.terning.terningserver.common.exception.enums.ErrorMessage.NOT_FOUND_USER_EXCEPTION;

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
        Grade grade = (responseDto.grade() == null || responseDto.grade().isBlank())
                ? null : Grade.fromKey(responseDto.grade());
        WorkingPeriod workingPeriod = (responseDto.workingPeriod() == null || responseDto.workingPeriod().isBlank())
                ? null : WorkingPeriod.fromKey(responseDto.workingPeriod());

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
