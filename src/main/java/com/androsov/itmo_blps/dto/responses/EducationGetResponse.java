package com.androsov.itmo_blps.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class EducationGetResponse {
    private Long id;

    private Long resumeId;
    private String level;
    private String form;
    private LocalDate endDate;
    private String universityName;
    private String faculty;
    private String specialization;
}
