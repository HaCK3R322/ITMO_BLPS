package com.androsov.itmo_blps.dto.requests;

import com.androsov.itmo_blps.annotations.NullOrNotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceCreateRequest {
    @NotBlank(message = "Position must not be blank")
    private String position;
    @NotBlank(message = "Organization name must not be blank")
    private String organizationName;
    @NullOrNotBlank(message = "Field must either be null or not blank")
    private String website;
    @NotBlank(message = "City must not be blank")
    private String city;
    @NullOrNotBlank(message = "Field must either be null or not blank")
    private String companyActivity;
    @Column(length = 2000)
    @NullOrNotBlank(message = "Field must either be null or not blank")
    private String responsibilities;
    @Column(length = 2000)
    @NullOrNotBlank(message = "Field must either be null or not blank")
    private String achievements;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "Field must not be null")
    private LocalDate endDate;
}