package com.androsov.itmo_blps_lab1.repositories;

import com.androsov.itmo_blps_lab1.model.entities.Resume;
import com.androsov.itmo_blps_lab1.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> getAllByUser(User user);
}