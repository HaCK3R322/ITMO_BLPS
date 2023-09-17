package com.androsov.itmo_blps.jobs;

import com.androsov.itmo_blps.servicies.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class StatisticsJob implements Job {
    private final StatisticsService statisticsService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        statisticsService.calculateAndSaveStatisticsSummary();
    }
}
