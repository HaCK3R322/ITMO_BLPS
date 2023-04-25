package com.androsov.itmo_blps_lab1.repositories;

import com.androsov.itmo_blps_lab1.entities.ResumeVacancyLink;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}