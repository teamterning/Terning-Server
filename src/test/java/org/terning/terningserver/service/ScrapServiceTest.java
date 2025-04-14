package org.terning.terningserver.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.terning.terningserver.domain.Company;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;
import org.terning.terningserver.domain.User;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.domain.enums.Color;
import org.terning.terningserver.domain.enums.CompanyCategory;
import org.terning.terningserver.repository.internship_announcement.InternshipRepository;
import org.terning.terningserver.repository.scrap.ScrapRepository;
import org.terning.terningserver.repository.user.UserRepository;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ScrapServiceTest {

    @Autowired
    private ScrapService scrapService;

    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired

    private UserRepository userRepository;

    @BeforeEach
    public void before() {
        Company company = new Company("info", CompanyCategory.OTHERS, "image");

        InternshipAnnouncement announcement = new InternshipAnnouncement(
                1L,
                "test 공고",
                LocalDate.now().plusDays(7),
                "3개월",
                2025,
                4,
                0,
                100,
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

    @AfterEach
    public void after() {
        scrapRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        internshipRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("동시에 여러 유저가 스크랩 취소 시도 시 scrapCount 감소가 정상적으로 처리된다.")
    public void requests_100_AtTheSameTime() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
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

        InternshipAnnouncement savedAnnouncement = internshipRepository.findById(1L).orElseThrow();
        assertThat(savedAnnouncement.getScrapCount()).isEqualTo(0L);
    }

}