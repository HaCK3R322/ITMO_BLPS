package com.androsov.itmo_blps_lab1.repositories;

import com.androsov.itmo_blps_lab1.model.entities.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    boolean existsById(Long id);
}