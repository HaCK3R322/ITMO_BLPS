package com.androsov.itmo_blps.dto;

import com.androsov.itmo_blps.annotations.NullOrNotBlank;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ResumeDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("imageId")
    @Null
    private Long imageId;

    // ================================================================================

    @NotBlank(message = "Name is required")
    @Length(min = 2, max = 50, message = "Name should be in range of 2-50 characters")
    private String name;

    @NotBlank(message = "Surname is required")
    @Length(min = 2, max = 50, message = "Surname should be in range of 2-50 characters")
    private String surname;

    @NotBlank(message = "Patronymic is required")
    @Length(min = 2, max = 50, message = "Patronymic should be in range of 2-50 characters")
    private String patronymic;

    @Past(message = "Date of birth should be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "City is required")
    private String city;

    @NullOrNotBlank(message = "Invalid metro station")
    private String metroStation;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Invalid phone number format")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    // ================================================================================

    @NullOrNotBlank(message = "Desired must be null or must not be blank")
    private String desiredPosition;

    @DecimalMin(value = "0.0", message = "Salary should not be negative")
    private Double salary;

    @Pattern(regexp = "^(Full|Part-time|Shift|Rotational|Partial Remote)$", message = "Invalid employment value")
    private String employment;

    // =================================================================================

    // work experience attached to resume by FK resume_id

    // =================================================================================

    @Pattern(regexp = "^(\\w*;)*$", message = "Invalid skills format. Use semicolons to separate skills.")
    private String skills;
}
