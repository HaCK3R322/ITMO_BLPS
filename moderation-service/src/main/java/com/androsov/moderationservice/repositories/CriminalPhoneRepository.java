package com.androsov.moderationservice.repositories;

import com.androsov.moderationservice.model.entities.CriminalPhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CriminalPhoneRepository extends JpaRepository<CriminalPhone, Long> {
    Optional<CriminalPhone> findCriminalPhoneByNumber(String number);
}
