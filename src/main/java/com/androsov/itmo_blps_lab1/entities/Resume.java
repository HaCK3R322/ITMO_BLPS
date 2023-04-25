package com.androsov.itmo_blps_lab1.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String specialization;
    private String name;
    private String surname;
    private Integer age;
    private String studyingDescription;
    private String jobsDescription;

    public Resume() {

    }
}
