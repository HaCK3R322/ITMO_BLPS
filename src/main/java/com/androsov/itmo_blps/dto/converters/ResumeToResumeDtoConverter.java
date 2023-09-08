package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.ResumeDto;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.resume.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class ResumeToResumeDtoConverter implements Converter<Resume, ResumeDto> {
    @Override
    public ResumeDto convert(Resume resume) {
        Image image = resume.getResumeImage(); // Use getResumeImage() to get the image associated with the resume
        Long imageId = (image != null) ? image.getId() : null;

        return new ResumeDto(
                resume.getId(),
                resume.getUser().getId(), // Assuming you want the user ID
                imageId,
                resume.getName(),
                resume.getSurname(),
                resume.getPatronymic(),
                resume.getDateOfBirth(),
                resume.getCity(),
                resume.getMetroStation(),
                resume.getPhoneNumber(),
                resume.getEmail(),
                resume.getDesiredPosition(),
                resume.getSalary(),
                resume.getEmployment(),
                resume.getSkills());
    }

    public List<ResumeDto> convert(List<Resume> resumes) {
        return resumes.stream()
                .map(this::convert)
                .toList();
    }
}

