package com.androsov.itmo_blps.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "statistics_summaries")
public class StatisticsSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "creation_time")
    private Timestamp creationTime;

    private Long numberOfUsers;
    private Long numberOfVacancies;
    private Long numberOfResumes;
}



