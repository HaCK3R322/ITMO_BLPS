package com.androsov.itmo_blps_lab1.controllers.dto.converters;

import com.androsov.itmo_blps_lab1.controllers.dto.ResumeDto;
import com.androsov.itmo_blps_lab1.model.entities.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;


@RequiredArgsConstructor
public class ResumeToResumeDtoConverter implements Converter<Resume, ResumeDto> {
    @Override
    public ResumeDto convert(Resume resume) {
        return new ResumeDto(resume.getId(),
                resume.getUser().getUsername(),
                resume.getSpecialization(),
                resume.getName(),
                resume.getSurname(),
                resume.getAge(),
                resume.getStudyingDescription(),
                resume.getJobsDescription());
    }
}
