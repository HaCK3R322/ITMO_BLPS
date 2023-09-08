package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.WorkExperienceDto;
import com.androsov.itmo_blps.entities.resume.Resume;
import com.androsov.itmo_blps.entities.resume.WorkExperience;
import com.androsov.itmo_blps.repositories.ResumeRepository;
import com.androsov.itmo_blps.repositories.WorkExperienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@AllArgsConstructor
public class WorkExperienceDtoToWorkExperienceConverter implements Converter<WorkExperienceDto, WorkExperience> {
    WorkExperienceRepository workExperienceRepository;
    ResumeRepository resumeRepository;

    @Override
    public WorkExperience convert(WorkExperienceDto workExperienceDto) throws EntityNotFoundException {
        if (workExperienceDto.getId() != null) { // if we work with some existing work experience
            return workExperienceRepository.findById(workExperienceDto.getId()).orElseThrow(EntityNotFoundException::new);
        }

        Resume resume = resumeRepository.findById(workExperienceDto.getResumeId()).orElseThrow(EntityNotFoundException::new);

        WorkExperience workExperience = new WorkExperience();

        workExperience.setResume(resume);
        workExperience.setPosition(workExperienceDto.getPosition());
        workExperience.setOrganizationName(workExperienceDto.getOrganizationName());
        workExperience.setWebsite(workExperienceDto.getWebsite());
        workExperience.setCity(workExperienceDto.getCity());
        workExperience.setCompanyActivity(workExperienceDto.getCompanyActivity());
        workExperience.setResponsibilities(workExperienceDto.getResponsibilities());
        workExperience.setAchievements(workExperienceDto.getAchievements());
        workExperience.setStartDate(workExperienceDto.getStartDate());
        workExperience.setEndDate(workExperienceDto.getEndDate());

        return workExperience;
    }

    public List<WorkExperience> convert(List<WorkExperienceDto> workExperienceDtos) {
        return workExperienceDtos.stream()
                .map(this::convert)
                .toList();
    }
}

