package com.androsov.itmo_blps_lab1.repositories;

import com.androsov.itmo_blps_lab1.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}