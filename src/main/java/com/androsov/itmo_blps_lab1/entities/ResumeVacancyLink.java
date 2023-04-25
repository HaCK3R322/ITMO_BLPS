package com.androsov.itmo_blps_lab1.entities;

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
}
