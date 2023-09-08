package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.entities.resume.Resume;
import com.androsov.itmo_blps.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> getAllByUser(User user);
}