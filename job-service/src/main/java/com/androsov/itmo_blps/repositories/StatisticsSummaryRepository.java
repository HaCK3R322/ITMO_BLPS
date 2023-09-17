package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.model.entities.StatisticsSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsSummaryRepository extends JpaRepository<StatisticsSummary, Long> {
}
