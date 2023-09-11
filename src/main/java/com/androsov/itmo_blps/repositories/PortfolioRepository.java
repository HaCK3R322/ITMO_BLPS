package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.model.entities.resume.Portfolio;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByResume(Resume resume);

    List<Portfolio> deleteAllByResume(Resume resume);
}
