package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.EducationDto;
import com.androsov.itmo_blps.dto.ResumeDto;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.resume.Education;
import com.androsov.itmo_blps.entities.resume.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EducationToEducationDtoConverter implements Converter<Education, EducationDto> {
    @Override
    public EducationDto convert(Education education) {
        EducationDto educationDto = new EducationDto();

        educationDto.setId(education.getId());
        educationDto.setResumeId(education.getResume().getId());
        educationDto.setLevel(education.getLevel());
        educationDto.setForm(education.getForm());
        educationDto.setEndDate(education.getEndDate());
        educationDto.setUniversityName(education.getUniversityName());
        educationDto.setFaculty(education.getFaculty());
        educationDto.setSpecialization(education.getSpecialization());

        return educationDto;
    }

    public List<EducationDto> convert(List<Education> educations) {
        return educations.stream()
                .map(this::convert)
                .toList();
    }

}
