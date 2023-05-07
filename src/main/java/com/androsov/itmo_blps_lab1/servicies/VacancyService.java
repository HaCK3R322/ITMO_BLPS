package com.androsov.itmo_blps_lab1.servicies;

import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.entities.ResumeVacancyLink;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import com.androsov.itmo_blps_lab1.repositories.ResumeVacancyLinkRepository;
import com.androsov.itmo_blps_lab1.repositories.VacancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    public Vacancy getById(Long id) throws ChangeSetPersister.NotFoundException {
        return vacancyRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public void attachResume(Vacancy vacancy, Resume resume) {
        resumeVacancyLinkRepository.save(new ResumeVacancyLink(vacancy, resume));
    }

    public boolean exists(Vacancy vacancy) {
        if(vacancy == null || vacancy.getId() == null) return false;

        return vacancyRepository.existsById(vacancy.getId());
    }
    public boolean existsById(Long id) {
        return vacancyRepository.existsById(id);
    }
}
