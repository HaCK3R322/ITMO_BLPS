package com.androsov.itmo_blps_lab1.dto.converters;

import com.androsov.itmo_blps_lab1.dto.VacancyDto;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VacancyToVacancyDtoConverter implements Converter<Vacancy, VacancyDto> {
    @Override
    public VacancyDto convert(Vacancy vacancy) {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setId(vacancy.getId());
        vacancyDto.setName(vacancy.getName());
        vacancyDto.setUsername(vacancy.getUser().getUsername());
        vacancyDto.setDescription(vacancy.getDescription());
        vacancyDto.setAddress(vacancy.getAddress());
        vacancyDto.setWorkExperience(vacancy.getWorkExperience());
        vacancyDto.setResponsibilities(vacancy.getResponsibilities());
        vacancyDto.setRequirements(vacancy.getRequirements());
        vacancyDto.setConditions(vacancy.getConditions());
        vacancyDto.setMinimumPayment(vacancy.getMinimumPayment());
        vacancyDto.setMaximumPayment(vacancy.getMaximumPayment());
        return vacancyDto;
    }

    public List<VacancyDto> convert(List<Vacancy> vacancies) {
        return vacancies.stream()
                .map(this::convert)
                .toList();
    }
}

