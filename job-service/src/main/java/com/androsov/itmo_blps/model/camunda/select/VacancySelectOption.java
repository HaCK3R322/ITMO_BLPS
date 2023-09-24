package com.androsov.itmo_blps.model.camunda.select;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VacancySelectOption {
    private String label;
    private Long value;
}
