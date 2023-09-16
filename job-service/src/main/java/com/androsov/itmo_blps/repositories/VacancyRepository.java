package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.entities.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    boolean existsById(Long id);
    boolean existsByUserIdAndName(Long userId, String name);

    Vacancy findByUserIdAndName(Long userId, String name);

}