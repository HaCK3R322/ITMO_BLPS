package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> getAllByUserId(Long userId);

    List<Resume> deleteAllByUserId(Long userId);
}