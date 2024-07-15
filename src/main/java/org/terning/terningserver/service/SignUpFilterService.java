package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.terning.terningserver.domain.Filter;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.dto.auth.request.SignUpFilterRequestDto;
import org.terning.terningserver.domain.enums.Grade;
import org.terning.terningserver.domain.enums.WorkingPeriod;
import org.terning.terningserver.exception.CustomException;
import org.terning.terningserver.repository.filter.FilterRepository;
import org.terning.terningserver.repository.user.UserRepository;

import static org.terning.terningserver.exception.enums.ErrorMessage.SIGN_UP_FILTER_FAILED;

@Service
@RequiredArgsConstructor
public class SignUpFilterService {

    private final FilterRepository filterRepository;
    private final UserRepository userRepository;

    public void signUpFilter(Long userId, SignUpFilterRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(SIGN_UP_FILTER_FAILED));

        int gradeKey = request.grade();
        int workingPeriodKey = request.workingPeriod();
        int startYear = request.startYear();
        int startMonth = request.startMonth();

        Grade grade = Grade.fromKey(gradeKey);
        WorkingPeriod workingPeriod = WorkingPeriod.fromKey(workingPeriodKey);

        Filter filter = Filter.builder()
                .grade(grade)
                .workingPeriod(workingPeriod)
                .startYear(startYear)
                .startMonth(startMonth)
                .build();

        try {
            filterRepository.save(filter);
        } catch (Exception e) {
            throw new CustomException(SIGN_UP_FILTER_FAILED);
        }


        user.updateFilter(grade, workingPeriod, startYear, startMonth);
        userRepository.save(user);
    }

}
