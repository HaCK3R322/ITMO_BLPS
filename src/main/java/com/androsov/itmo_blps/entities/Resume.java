package com.androsov.itmo_blps.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "resumes")
@ToString
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(min = 5, max = 100, message = "Specialization should be in range of 5-100 symbols")
    private String specialization;

    @Size(min = 2, max = 50, message = "Name should be in range of 2-50 symbols")
    private String name;
    @Size(min = 2, max = 50, message = "Surname should be in range of 2-50 symbols")

    private String surname;

    @Min(value = 18, message = "Age should not be less that 18")
    @Max(value = 150, message = "Age should not be grater that 150")
    private Integer age;

    @Size(max = 2000, message = "Studying description should not be longer that 2000 symbols")
    private String studyingDescription;
    @Size(max = 2000, message = "Jobs description should not be longer that 2000 symbols")
    private String jobsDescription;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Resume() {

    }
}
