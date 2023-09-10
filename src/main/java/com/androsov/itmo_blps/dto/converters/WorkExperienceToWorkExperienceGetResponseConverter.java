package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.responses.WorkExperienceGetResponse;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WorkExperienceToWorkExperienceGetResponseConverter implements Converter<WorkExperience, WorkExperienceGetResponse> {
    @Override
    public WorkExperienceGetResponse convert(WorkExperience source) {
        WorkExperienceGetResponse response = new WorkExperienceGetResponse();

        response.setId(source.getId());
        response.setResumeId(source.getResume().getId());
        response.setPosition(source.getPosition());
        response.setOrganizationName(source.getOrganizationName());
        response.setWebsite(source.getWebsite());
        response.setCity(source.getCity());
        response.setCompanyActivity(source.getCompanyActivity());
        response.setResponsibilities(source.getResponsibilities());
        response.setAchievements(source.getAchievements());
        response.setStartDate(source.getStartDate());
        response.setEndDate(source.getEndDate());

        return response;
    }
}
