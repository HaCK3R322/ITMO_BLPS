package com.androsov.moderationservice.repositories;

import com.androsov.moderationservice.model.entities.Criminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CriminalRepository extends JpaRepository<Criminal,Long> {
    Optional<Criminal> findByNameAndSurnameAndPatronymicAndDateOfBirth(String name, String surname, String patronymic, LocalDate dateOfBirth);
    Optional<Criminal> findByNameAndSurnameAndPatronymicAndCity(String name, String surname, String patronymic, String city);
}
