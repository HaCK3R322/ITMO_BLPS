package com.androsov.itmo_blps.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PortfolioGetResponse {
    private Long id;
    private Long resumeId;
    private String description;
    private Long imageId;

}
