package com.androsov.itmo_blps.configuration;

import com.androsov.itmo_blps.jobs.StatisticsJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuartzConfiguration {
    @Value("${spring.stats.sleep-time}")
    private Integer sleepTime;

    @Bean
    public JobDetail statisticsJobDetail() {
        return JobBuilder.newJob(StatisticsJob.class)
                .withIdentity("statisticsJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger statisticsJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(sleepTime)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(statisticsJobDetail())
                .withIdentity("statisticsTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}

