package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}