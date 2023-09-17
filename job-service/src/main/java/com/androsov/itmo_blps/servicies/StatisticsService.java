package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.model.entities.StatisticsSummary;
import com.androsov.itmo_blps.repositories.ResumeRepository;
import com.androsov.itmo_blps.repositories.StatisticsSummaryRepository;
import com.androsov.itmo_blps.repositories.UserRepository;
import com.androsov.itmo_blps.repositories.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final VacancyRepository vacancyRepository;
    private final StatisticsSummaryRepository statisticsSummaryRepository;

    public void calculateAndSaveStatisticsSummary() {
        StatisticsSummary stat = new StatisticsSummary();
        stat.setCreationTime(Timestamp.from(Instant.now()));
        stat.setNumberOfUsers(userRepository.count());
        stat.setNumberOfResumes(resumeRepository.count());
        stat.setNumberOfVacancies(vacancyRepository.count());

        statisticsSummaryRepository.save(stat);
    }
}
