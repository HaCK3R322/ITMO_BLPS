package com.androsov.itmo_blps_lab1.servicies;

import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.entities.ResumeVacancyLink;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import com.androsov.itmo_blps_lab1.repositories.ResumeVacancyLinkRepository;
import com.androsov.itmo_blps_lab1.repositories.VacancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VacancyService {
    private VacancyRepository vacancyRepository;
    private ResumeVacancyLinkRepository resumeVacancyLinkRepository;

    public Vacancy createVacancy(Vacancy vacancy) {
        if (vacancyRepository.existsByUserAndName(vacancy.getUser(), vacancy.getName()))
            throw new IllegalArgumentException("Vacancy with user '" + vacancy.getUser() +
                    "' and name '" + vacancy.getName() + "' already exists.");

        return vacancyRepository.save(vacancy);
    }

    public void attachResume(Vacancy vacancy, Resume resume) {
        resumeVacancyLinkRepository.save(new ResumeVacancyLink(vacancy, resume));
    }
}
