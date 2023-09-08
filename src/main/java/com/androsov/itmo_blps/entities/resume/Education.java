package com.androsov.itmo_blps.entities.resume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private String level;

    private String form;

    private LocalDate endDate;

    private String universityName;

    private String faculty;

    private String specialization;

    public Education() {
    }
}