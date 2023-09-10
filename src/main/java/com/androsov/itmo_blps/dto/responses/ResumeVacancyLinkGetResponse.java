package com.androsov.itmo_blps.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResumeVacancyLinkGetResponse {
    private Long id;
    private Long vacancyId;
    private Long resumeId;
}
