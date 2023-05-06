package com.androsov.itmo_blps_lab1.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "address")
    private String address;
    @Column(name = "work_experience")
    private String workExperience;
    @Column(name = "responsibilities")
    private String responsibilities;
    @Column(name = "requirements")
    private String requirements;
    @Column(name = "conditions")
    private String conditions;
    @Column(name = "minimum_payment")
    private Integer minimumPayment;
    @Column(name = "maximum_payment")
    private Integer maximumPayment;

    @Lob
    @Column(name = "image")
    private byte[] image;

    public Vacancy() {

    }
}
