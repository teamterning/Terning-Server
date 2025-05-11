package org.terning.terningserver.external.pushNotification.user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class UserSyncJobConfig {

    public static final String USER_SYNC_JOB = "userSyncJob";
    public static final String USER_SYNC_STEP = "userSyncStep";

    private final NotificationSyncTasklet notificationSyncTasklet;

    @Bean
    public Job userSyncJob(JobRepository jobRepository, Step userSyncStep) {
        return new JobBuilder(USER_SYNC_JOB, jobRepository)
                .start(userSyncStep)
                .build();
    }

    @Bean
    public Step userSyncStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(USER_SYNC_STEP, jobRepository)
                .tasklet(notificationSyncTasklet, transactionManager)
                .build();
    }
}
