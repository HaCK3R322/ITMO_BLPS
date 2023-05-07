package com.androsov.itmo_blps_lab1.servicies;

import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.entities.ResumeVacancyLink;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import com.androsov.itmo_blps_lab1.repositories.ResumeVacancyLinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResumeVacancyLinkService {
    private ResumeVacancyLinkRepository resumeVacancyLinkRepository;

    public ResumeVacancyLink create(Resume resume, Vacancy vacancy) {
        ResumeVacancyLink resumeVacancyLink = new ResumeVacancyLink(null, resume, vacancy);

        return resumeVacancyLinkRepository.save(resumeVacancyLink);
    }
}
