package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.model.entities.resume.Education;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> getAllByResume(Resume resume);
    List<Education> deleteAllByResume(Resume resume);
}
