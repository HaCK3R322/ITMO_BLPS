package com.androsov.itmo_blps.model.entities.resume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private String position;

    private String organizationName;

    private String website;

    private String city;

    private String companyActivity;

    @Column(length = 2000)
    private String responsibilities;

    @Column(length = 2000)
    private String achievements;

    private LocalDate startDate;

    private LocalDate endDate;

    public WorkExperience() {
    }
}
