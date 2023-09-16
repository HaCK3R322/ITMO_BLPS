package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.responses.VacancyGetResponse;
import com.androsov.itmo_blps.model.entities.Vacancy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VacancyToVacancyGetResponseConverter implements Converter<Vacancy, VacancyGetResponse> {
    public VacancyGetResponse convert(Vacancy vacancy) {
        VacancyGetResponse response = new VacancyGetResponse();

        response.setId(vacancy.getId());
        response.setUserId(vacancy.getUserId());
        response.setName(vacancy.getName());
        response.setDescription(vacancy.getDescription());
        response.setCity(vacancy.getCity());
        response.setAddress(vacancy.getAddress());
        response.setRequiredWorkExperience(vacancy.getRequiredWorkExperience());
        response.setResponsibilities(vacancy.getResponsibilities());
        response.setRequirements(vacancy.getRequirements());
        response.setConditions(vacancy.getConditions());
        response.setSalaryFrom(vacancy.getSalaryFrom());
        response.setSalaryTo(vacancy.getSalaryTo());

        return response;
    }
}
