package com.androsov.itmo_blps_lab1.controllers.dto;

import com.androsov.itmo_blps_lab1.model.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
public class ResumeDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @Size(min = 5, max = 100, message = "Specialization should be in range of 5-100 symbols")
    private String specialization;

    @Size(min = 2, max = 50, message = "Name should be in range of 2-50 symbols")
    @NotBlank(message = "Name required")
    @NotNull(message = "Name required")
    private String name;
    @Size(min = 2, max = 50, message = "Surname should be in range of 2-50 symbols")
    @NotBlank(message = "Name required")
    @NotNull(message = "Name required")
    private String surname;

    @Min(value = 18, message = "Age should not be less that 18")
    @Max(value = 150, message = "Age should not be grater that 150")
    @NotBlank(message = "Age required")
    @NotNull(message = "Age required")
    private Integer age;

    @Size(max = 2000, message = "Studying description should not be longer that 2000 symbols")
    private String studyingDescription;
    @Size(max = 2000, message = "Jobs description should not be longer that 2000 symbols")
    private String jobsDescription;
}
