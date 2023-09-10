package com.androsov.itmo_blps.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeGetResponse {
    private Long id;
    private Long userId;
    private Long resumeImageId;

//    private List<Long> workExperienceIds;
//    private List<Long> educationIds;
//    private List<Long> portfolioIds;

    // ===================================

    private String name;
    private String surname;
    private String patronymic;
    private LocalDate dateOfBirth;
    private String city;
    private String metroStation;
    private String phoneNumber;
    private String email;
    private String desiredPosition;
    private Double salary;
    private String employment;
    private String skills;
}

