package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.model.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> deleteAllByUserId(Long userId);
}