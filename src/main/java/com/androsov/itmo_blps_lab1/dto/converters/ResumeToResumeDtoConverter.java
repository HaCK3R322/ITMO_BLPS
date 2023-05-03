package com.androsov.itmo_blps_lab1.dto.converters;

import com.androsov.itmo_blps_lab1.dto.ResumeDto;
import com.androsov.itmo_blps_lab1.model.entities.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.stream.Collectors;


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

    public List<ResumeDto> convert(List<Resume> resumes) {

        return resumes.stream()
                .map(this::convert)
                .toList();
    }
}
