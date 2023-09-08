package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.resume.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
