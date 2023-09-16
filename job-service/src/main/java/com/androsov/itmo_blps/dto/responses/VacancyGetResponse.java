package com.androsov.itmo_blps.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacancyGetResponse {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String city;
    private String address;
    private String requiredWorkExperience;
    private String responsibilities;
    private String requirements;
    private String conditions;
    private Integer salaryFrom;
    private Integer salaryTo;
}
