package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.entities.resume.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
