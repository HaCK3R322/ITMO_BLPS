package com.androsov.itmo_blps.dto.convertersss;

import com.androsov.itmo_blps.dto.WorkExperienceDto;
import com.androsov.itmo_blps.entities.resume.WorkExperience;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WorkExperienceToWorkExperienceDtoConverter implements Converter<WorkExperience, WorkExperienceDto> {
    @Override
    public WorkExperienceDto convert(WorkExperience workExperience) {
        WorkExperienceDto workExperienceDto = new WorkExperienceDto();

        workExperienceDto.setId(workExperience.getId());
        workExperienceDto.setResumeId(workExperience.getResume().getId());
        workExperienceDto.setPosition(workExperience.getPosition());
        workExperienceDto.setOrganizationName(workExperience.getOrganizationName());
        workExperienceDto.setWebsite(workExperience.getWebsite());
        workExperienceDto.setCity(workExperience.getCity());
        workExperienceDto.setCompanyActivity(workExperience.getCompanyActivity());
        workExperienceDto.setResponsibilities(workExperience.getResponsibilities());
        workExperienceDto.setAchievements(workExperience.getAchievements());
        workExperienceDto.setStartDate(workExperience.getStartDate());
        workExperienceDto.setEndDate(workExperience.getEndDate());

        return workExperienceDto;
    }

    public List<WorkExperienceDto> convert(List<WorkExperience> workExperiences) {
        return workExperiences.stream()
                .map(this::convert)
                .toList();
    }
}

