package com.androsov.itmo_blps.dto.requests;

import lombok.Data;

import javax.validation.constraints.Null;
import java.util.List;

@Data
public class VacancySearchParams {
    @Null
    private List<String> keyWords;
    @Null
    private List<String> cities;
    @Null
    private Integer salaryFrom;
    @Null
    private Integer salaryTo;
}
