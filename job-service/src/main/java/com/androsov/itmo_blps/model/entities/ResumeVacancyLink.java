package com.androsov.itmo_blps.model.entities;

import com.androsov.itmo_blps.model.entities.resume.Resume;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "resume_vacancy_link")
public class ResumeVacancyLink {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Resume resume;

    @OneToOne
    private Vacancy vacancy;

    public ResumeVacancyLink() {

    }

    public ResumeVacancyLink(Vacancy vacancy, Resume resume) {
        this.resume = resume;
        this.vacancy = vacancy;
    }

}
