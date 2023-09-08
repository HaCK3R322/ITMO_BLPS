package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.entities.resume.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
}
