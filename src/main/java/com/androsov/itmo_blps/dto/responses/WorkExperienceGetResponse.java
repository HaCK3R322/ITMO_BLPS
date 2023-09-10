package com.androsov.itmo_blps.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class WorkExperienceGetResponse {
    private Long id;
    private Long resumeId;
    private String position;
    private String organizationName;
    private String website;
    private String city;
    private String companyActivity;
    private String responsibilities;
    private String achievements;
    private LocalDate startDate;
    private LocalDate endDate;
}
