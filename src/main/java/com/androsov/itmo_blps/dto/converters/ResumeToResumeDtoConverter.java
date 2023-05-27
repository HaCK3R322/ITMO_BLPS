package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.ResumeDto;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class ResumeToResumeDtoConverter implements Converter<Resume, ResumeDto> {
    @Override
    public ResumeDto convert(Resume resume) {
        Image image = resume.getImage();
        Long imageId = null;
        if (image != null) imageId = image.getId();

        return new ResumeDto(
                resume.getId(),
                resume.getUser().getUsername(),
                resume.getSpecialization(),
                resume.getName(),
                resume.getSurname(),
                resume.getAge(),
                resume.getStudyingDescription(),
                resume.getJobsDescription(),
                imageId);
    }

    public List<ResumeDto> convert(List<Resume> resumes) {

        return resumes.stream()
                .map(this::convert)
                .toList();
    }
}
