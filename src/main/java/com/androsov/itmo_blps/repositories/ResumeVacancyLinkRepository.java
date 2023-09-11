package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeVacancyLinkRepository extends JpaRepository<ResumeVacancyLink, Long> {
    List<ResumeVacancyLink> deleteAllByResume(Resume resume);
}