package org.terning.terningserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

//    @Transactional
//    public void signUpFilter(Long userId, SignUpFilterRequestDto request) {
//
//        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(SIGN_UP_FILTER_FAILED));
//
//        int gradeKey = request.grade();
//        int workingPeriodKey = request.workingPeriod();
//        int startYear = request.startYear();
//        int startMonth = request.startMonth();
//
//        Grade grade = Grade.fromKey(gradeKey);
//        WorkingPeriod workingPeriod = WorkingPeriod.fromKey(workingPeriodKey);
//
//        Filter filter = Filter.builder()
//                .grade(grade)
//                .workingPeriod(workingPeriod)
//                .startYear(startYear)
//                .startMonth(startMonth)
//                .build();
//
//        try {
//            filterRepository.save(filter);
//        } catch (Exception e) {
//            throw new CustomException(SIGN_UP_FILTER_FAILED);
//        }
//
//
//        user.updateFilter(grade, workingPeriod, startYear, startMonth);
//        userRepository.save(user);
//    }


    @Transactional
    public Filter createAndSaveFilter(SignUpFilterRequestDto request) {

        // 필터 데이터 생성
        Grade grade = Grade.fromKey(request.grade());
        WorkingPeriod workingPeriod = WorkingPeriod.fromKey(request.workingPeriod());
        int startYear = request.startYear();
        int startMonth = request.startMonth();

        // 필터 객체 생성
        Filter filter = Filter.builder()
                .grade(grade)
                .workingPeriod(workingPeriod)
                .startYear(startYear)
                .startMonth(startMonth)
                .build();

        // 필터 저장
        return filterRepository.save(filter);
    }

    @Transactional
    public void connectFilterToUser(Long userId, Long filterId) {
        // 사용자 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(SIGN_UP_FILTER_FAILED));
        Filter filter = filterRepository.findById(filterId).orElseThrow(() -> new CustomException(SIGN_UP_FILTER_FAILED));

        // 사용자 객체에 필터 할당
        user.assignFilter(filter);

        // 사용자 정보 업데이트
        userRepository.save(user);
    }

}
