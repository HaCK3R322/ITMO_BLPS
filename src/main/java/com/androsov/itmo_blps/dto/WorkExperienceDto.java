package com.androsov.itmo_blps.dto;

import com.androsov.itmo_blps.annotations.NullOrNotBlank;
import com.androsov.itmo_blps.entities.resume.Resume;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("resumeId")
    private Long resumeId;

    @NotBlank
    private String position;

    @NotBlank
    private String organizationName;

    @NullOrNotBlank
    private String website;

    @NotBlank
    private String city;

    @NullOrNotBlank
    private String companyActivity;

    @Column(length = 2000)
    @NullOrNotBlank
    private String responsibilities;

    @Column(length = 2000)
    @NullOrNotBlank
    private String achievements;

    @NotNull
    private LocalDate startDate;

    @NullOrNotBlank
    private LocalDate endDate;

}
