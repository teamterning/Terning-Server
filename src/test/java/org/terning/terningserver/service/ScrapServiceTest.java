package org.terning.terningserver.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.terning.terningserver.internshipAnnouncement.domain.Company;
import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;
import org.terning.terningserver.scrap.application.ScrapService;
import org.terning.terningserver.scrap.domain.Scrap;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.user.domain.AuthType;
import org.terning.terningserver.scrap.domain.Color;
import org.terning.terningserver.internshipAnnouncement.domain.CompanyCategory;
import org.terning.terningserver.scrap.dto.request.CreateScrapRequestDto;
import org.terning.terningserver.internshipAnnouncement.repository.InternshipRepository;
import org.terning.terningserver.scrap.repository.ScrapRepository;
import org.terning.terningserver.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("test")
class ScrapServiceTest {

    @Autowired private ScrapService scrapService;
    @Autowired private ScrapRepository scrapRepository;
    @Autowired private InternshipRepository internshipRepository;
    @Autowired private UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        scrapRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        internshipRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("스크랩 추가 테스트")
    class CreateScrapTest {

        @BeforeEach
        public void setup() {
            Company company = new Company("info", CompanyCategory.OTHERS, "image");

            InternshipAnnouncement announcement = new InternshipAnnouncement(
                    1L,
                    "test 공고",
                    LocalDate.now().plusDays(7),
                    "3개월",
                    2025,
                    4,
                    0,
                    5,
                    "https://mock.com",
                    null,
                    company,
                    "자격요건",
                    "직무 유형",
                    "상세 내용",
                    false
            );

            internshipRepository.save(announcement);

            for (int i = 0; i < 5; i++) {
                User user = User.builder()
                        .authId("user" + i)
                        .name("test" + i)
                        .authType(AuthType.APPLE)
                        .build();
                userRepository.save(user);

                Scrap scrap = Scrap.create(user, announcement, Color.BLUE);
                scrapRepository.save(scrap);
            }

            for (int i = 5; i < 105; i++) {
                User user = User.builder()
                        .authId("user" + i)
                        .name("test" + i)
                        .authType(AuthType.APPLE)
                        .build();
                userRepository.save(user);
            }
        }

        @Test
        @DisplayName("동시에 여러 유저가 스크랩 추가 시 scrapCount 증가가 정상적으로 처리된다.")
        public void 동시에_여러_유저가_스크랩_추가() throws InterruptedException {
            int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);

            CreateScrapRequestDto requestDto = new CreateScrapRequestDto("red");

            for (int i = 5; i < 105; i++) {
                long userId = userRepository.findByAuthId("user" + i).orElseThrow().getId();
                executorService.submit(() -> {
                    try {
                        scrapService.createScrap(1L, requestDto, userId);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            executorService.shutdown();


            InternshipAnnouncement savedAnnouncement = internshipRepository.findById(1L).orElseThrow();
            assertThat(savedAnnouncement.getScrapCount()).isEqualTo(105L);
            assertThat(scrapRepository.count()).isEqualTo(105L);

        }
    }

    @Nested
    @DisplayName("스크랩 취소 테스트")
    class DeleteScrapTest {

        @BeforeEach
        public void setup() {
            Company company = new Company("info", CompanyCategory.OTHERS, "image");

            InternshipAnnouncement announcement = new InternshipAnnouncement(
                    1L,
                    "test 공고",
                    LocalDate.now().plusDays(7),
                    "3개월",
                    2025,
                    4,
                    0,
                    100, // scrapCount = 100
                    "https://mock.com",
                    null,
                    company,
                    "자격요건",
                    "직무 유형",
                    "상세 내용",
                    false
            );

            internshipRepository.save(announcement);

            for (int i = 0; i < 100; i++) {
                User user = User.builder()
                        .authId("user" + i)
                        .name("test" + i)
                        .authType(AuthType.APPLE)
                        .build();
                userRepository.save(user);

                Scrap scrap = Scrap.create(user, announcement, Color.BLUE);
                scrapRepository.save(scrap);
            }
        }

        @Test
        @DisplayName("동시에 여러 유저가 스크랩 취소 시 scrapCount 감소가 정상적으로 처리된다.")
        public void 동시에_여러_유저가_스크랩_취소() throws InterruptedException {
            int threadCount = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(32);
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < 100; i++) {
                long userId = userRepository.findByAuthId("user" + i).orElseThrow().getId();
                executorService.submit(() -> {
                    try {
                        scrapService.deleteScrap(1L, userId);
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            executorService.shutdown();

            InternshipAnnouncement savedAnnouncement = internshipRepository.findById(1L).orElseThrow();
            assertThat(savedAnnouncement.getScrapCount()).isEqualTo(0L);
            assertThat(scrapRepository.count()).isEqualTo(0L);
        }

        @Test
        @DisplayName("스크랩 취소시에 Unchecked Exception 발생 시 트랜잭션 롤백이 정상적으로 처리된다.")
        public void 트랜잭션_롤백_테스트() {
            Long userId = 10000L;
            Long internshipAnnouncementId = 1L;

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                scrapService.deleteScrap(internshipAnnouncementId, userId);
            });

            InternshipAnnouncement savedAnnouncement = internshipRepository.findById(internshipAnnouncementId).orElseThrow();
            assertThat(exception.getMessage()).isEqualTo("스크랩 정보가 존재하지 않습니다");
            assertThat(savedAnnouncement.getScrapCount()).isEqualTo(100L);
        }
    }
}