package com.androsov.itmo_blps.dto.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class VacancyCreateRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name cannot exceed {max} characters")
    @NotNull
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 2000, message = "Description cannot exceed {max} characters")
    private String description;

    @NotBlank(message = "City cannot be blank")
    @NotNull
    @Size(max = 100, message = "City cannot exceed {max} characters")
    private String city;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address cannot exceed {max} characters")
    private String address;

    @NotBlank(message = "Work experience cannot be blank")
    @Size(max = 255, message = "Work experience cannot exceed {max} characters")
    private String requiredWorkExperience;

    @NotBlank(message = "Responsibilities cannot be blank")
    @Size(max = 2000, message = "Responsibilities cannot exceed {max} characters")
    private String responsibilities;

    @NotBlank(message = "Requirements cannot be blank")
    @Size(max = 2000, message = "Requirements cannot exceed {max} characters")
    private String requirements;

    @NotBlank(message = "Conditions cannot be blank")
    @Size(max = 2000, message = "Conditions cannot exceed {max} characters")
    private String conditions;

    @NotNull(message = "Minimum salary cannot be null")
    @PositiveOrZero(message = "Minimum salary cannot be negative")
    private Integer salaryFrom;

    @NotNull(message = "Maximum salary cannot be null")
    @PositiveOrZero(message = "Maximum salary cannot be negative")
    private Integer salaryTo;
}
