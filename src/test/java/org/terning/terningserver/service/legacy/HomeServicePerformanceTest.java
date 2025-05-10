//package org.terning.terningserver.service.legacy;
//
//import com.querydsl.core.Tuple;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.Mockito;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import org.terning.terningserver.internshipAnnouncement.domain.Company;
//import org.terning.terningserver.filter.domain.Filter;
//import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;
//import org.terning.terningserver.user.domain.User;
//import org.terning.terningserver.filter.domain.Grade;
//import org.terning.terningserver.filter.domain.WorkingPeriod;
//import org.terning.terningserver.home.dto.response.HomeAnnouncementsResponseDto;
//import org.terning.terningserver.internshipAnnouncement.repository.InternshipRepository;
//import org.terning.terningserver.repository.UserRepository;
//import org.terning.terningserver.home.application.HomeServiceImpl;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.terning.terningserver.domain.QInternshipAnnouncement.internshipAnnouncement;
//import static org.terning.terningserver.domain.QScrap.scrap;
//
//@SpringBootTest
//@Transactional
//class HomeServicePerformanceTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(HomeServicePerformanceTest.class);
//
//    @Autowired
//    private HomeServiceImpl homeService;
//
//    private UserRepository userRepository;
//    private InternshipRepository internshipRepository;
//
//    @BeforeEach
//    void setUp() {
//        userRepository = Mockito.mock(UserRepository.class);
//        internshipRepository = Mockito.mock(InternshipRepository.class);
//        homeService = new HomeServiceImpl(internshipRepository, userRepository);
//
//        setUpMockData();
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideTestParameters")
//    @DisplayName("성능 테스트: 다양한 조건에서 공고 데이터 조회")
//    void testGetAnnouncementsPerformance(TestParameters params) {
//        logTestConditions(params);
//
//        long startTime = System.currentTimeMillis();
//        HomeAnnouncementsResponseDto response = homeService.getAnnouncements(
//                params.userId, params.sortBy);
//        long endTime = System.currentTimeMillis();
//
//        validateResponse(response);
//        logTestResults(response, endTime - startTime);
//    }
//
//    private void setUpMockData() {
//        User mockUser1 = createMockUser(1L, Grade.SENIOR, WorkingPeriod.OPTION2, 2024, 1);
//        User mockUser2 = createMockUser(2L, Grade.JUNIOR, WorkingPeriod.OPTION1, 2023, 12);
//
//        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser1));
//        Mockito.when(userRepository.findById(2L)).thenReturn(java.util.Optional.of(mockUser2));
//
//        List<Tuple> singleAnnouncement = createMockAnnouncementTuples(1);
//        List<Tuple> hundredAnnouncements = createMockAnnouncementTuples(100);
//        List<Tuple> thousandAnnouncements = createMockAnnouncementTuples(1000);
//
//        Mockito.when(internshipRepository.findFilteredInternshipsWithScrapInfo(Mockito.eq(mockUser1), Mockito.any()))
//                .thenReturn(singleAnnouncement);
//        Mockito.when(internshipRepository.findFilteredInternshipsWithScrapInfo(Mockito.eq(mockUser1), Mockito.any()))
//                .thenReturn(hundredAnnouncements);
//        Mockito.when(internshipRepository.findFilteredInternshipsWithScrapInfo(Mockito.eq(mockUser2), Mockito.any()))
//                .thenReturn(thousandAnnouncements);
//    }
//
//    private List<Tuple> createMockAnnouncementTuples(int count) {
//        List<Tuple> tuples = new ArrayList<>();
//        for (int i = 1; i <= count; i++) {
//            InternshipAnnouncement announcement = createMockAnnouncement(
//                    (long) i,
//                    "터닝 인턴쉽 " + i,
//                    LocalDate.of(2024, 12, 31),
//                    "6 months",
//                    2024,
//                    1
//            );
//            Tuple tuple = Mockito.mock(Tuple.class);
//            Mockito.when(tuple.get(internshipAnnouncement)).thenReturn(announcement);
//            Mockito.when(tuple.get(scrap.id)).thenReturn(null);
//            Mockito.when(tuple.get(scrap.color)).thenReturn(null);
//            tuples.add(tuple);
//        }
//        return tuples;
//    }
//
//    private User createMockUser(Long id, Grade grade, WorkingPeriod period, int startYear, int startMonth) {
//        Filter filter = Filter.builder()
//                .grade(grade)
//                .workingPeriod(period)
//                .startYear(startYear)
//                .startMonth(startMonth)
//                .build();
//        return User.builder()
//                .id(id)
//                .filter(filter)
//                .build();
//    }
//
//    private InternshipAnnouncement createMockAnnouncement(Long id, String title, LocalDate deadline, String period, int startYear, int startMonth) {
//        Company mockCompany = Mockito.mock(Company.class);
//        Mockito.when(mockCompany.getCompanyImage()).thenReturn("https://example.com/company/image.png");
//
//        InternshipAnnouncement announcement = Mockito.mock(InternshipAnnouncement.class);
//        Mockito.when(announcement.getId()).thenReturn(id);
//        Mockito.when(announcement.getTitle()).thenReturn(title);
//        Mockito.when(announcement.getDeadline()).thenReturn(deadline);
//        Mockito.when(announcement.getWorkingPeriod()).thenReturn(period);
//        Mockito.when(announcement.getStartYear()).thenReturn(startYear);
//        Mockito.when(announcement.getStartMonth()).thenReturn(startMonth);
//        Mockito.when(announcement.getCompany()).thenReturn(mockCompany);
//        return announcement;
//    }
//
//    private void logTestConditions(TestParameters params) {
//        logger.info("=======================================");
//        logger.info("🟢 [성능 테스트 시작]");
//        logger.info("---------------------------------------");
//        logger.info("▶ 테스트 조건:");
//        logger.info("   - User ID       : {}", params.userId);
//        logger.info("   - Sort By       : {}", params.sortBy);
//        logger.info("   - Start Year    : {}", params.startYear);
//        logger.info("   - Start Month   : {}", params.startMonth);
//        logger.info("---------------------------------------");
//    }
//
//    private void validateResponse(HomeAnnouncementsResponseDto response) {
//        logger.info("🟡 [응답 데이터 검증]");
//        logger.info("   - 응답 총 공고 수 : {}", response.totalCount());
//        if (response.totalCount() <= 0) {
//            logger.error("❌ 예상된 데이터가 없습니다. totalCount = 0");
//        }
//        assertThat(response).isNotNull();
//        assertThat(response.totalCount()).isGreaterThan(0);
//        assertThat(response.result()).isNotEmpty();
//    }
//
//    private void logTestResults(HomeAnnouncementsResponseDto response, long elapsedTime) {
//        logger.info("🟡 [테스트 결과]");
//        logger.info("   - 총 공고 수        : {}", response.totalCount());
//        logger.info("   - 결과 목록 크기    : {}", response.result().size());
//        logger.info("   - 실행 시간         : {}ms", elapsedTime);
//        logger.info("---------------------------------------");
//    }
//
//    static Stream<TestParameters> provideTestParameters() {
//        return Stream.of(
//                new TestParameters(1L, "date", 2024, 1),
//                new TestParameters(1L, "popularity", 2023, 12),
//                new TestParameters(2L, "date", 2024, 2)
//        );
//    }
//
//    static class TestParameters {
//        Long userId;
//        String sortBy;
//        int startYear;
//        int startMonth;
//
//        public TestParameters(Long userId, String sortBy, int startYear, int startMonth) {
//            this.userId = userId;
//            this.sortBy = sortBy;
//            this.startYear = startYear;
//            this.startMonth = startMonth;
//        }
//    }
//}
//
