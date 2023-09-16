package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    List<WorkExperience> getAllByResume(Resume resume);
    List<WorkExperience> deleteAllByResume(Resume resume);
}
