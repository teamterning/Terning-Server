package org.terning.terningserver.external.pushNotification.user.scheduler;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSyncScheduler {

    private static final String JOB_PARAM_TIMESTAMP = "timestamp";

    private final JobLauncher jobLauncher;
    private final Job userSyncJob;

    /**
     * 매주 목, 토 오후 12:30에 실행 (푸시 알림 전송 30분 전)
     */
    @Scheduled(cron = "0 30 12 ? * THU,SAT", zone = "Asia/Seoul")
    public void runUserSyncJobBeforeRecommendation() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong(JOB_PARAM_TIMESTAMP, System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(userSyncJob, jobParameters);
        log.info("User sync job for push status/name update (before recommendation) triggered at {}", LocalDateTime.now());
    }

    /**
     * 매주 일요일 오후 8:30에 실행 (푸시 알림 전송 30분 전)
     */
    @Scheduled(cron = "0 30 20 ? * SUN", zone = "Asia/Seoul")
    public void runUserSyncJobBeforeTrendingAlert() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong(JOB_PARAM_TIMESTAMP, System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(userSyncJob, jobParameters);
        log.info("User sync job for push status/name update (before trending alert) triggered at {}", LocalDateTime.now());
    }
}
