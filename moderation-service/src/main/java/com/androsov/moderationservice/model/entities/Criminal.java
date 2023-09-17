package com.androsov.moderationservice.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "criminals")
public class Criminal {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate dateOfBirth;
    private String city;

}
