package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.model.entities.Vacancy;
import com.androsov.itmo_blps.repositories.ResumeVacancyLinkRepository;
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
