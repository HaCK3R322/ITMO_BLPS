package com.androsov.itmo_blps_lab1.repositories;

import com.androsov.itmo_blps_lab1.entities.User;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    boolean existsById(Long id);
    boolean existsByUserAndName(User user, String name);

    Vacancy findByUserAndName(User user, String name);

}