package com.androsov.itmo_blps.dto.requests;

import lombok.Data;

import java.util.List;

@Data
public class VacancySearchParams {
    // TODO: накрутить проверок
    private List<String> keyWords;
    private List<String> cities;
    private Integer salaryFrom;
    private Integer salaryTo;
}
