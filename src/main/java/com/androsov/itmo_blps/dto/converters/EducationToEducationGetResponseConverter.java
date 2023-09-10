package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.responses.EducationGetResponse;
import com.androsov.itmo_blps.entities.resume.Education;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EducationToEducationGetResponseConverter implements Converter<Education, EducationGetResponse> {
    @Override
    public EducationGetResponse convert(Education education) {
        EducationGetResponse response = new EducationGetResponse();

        response.setId(education.getId());
        response.setResumeId(education.getResume().getId());
        response.setLevel(education.getLevel());
        response.setForm(education.getForm());
        response.setEndDate(education.getEndDate());
        response.setUniversityName(education.getUniversityName());
        response.setFaculty(education.getFaculty());
        response.setSpecialization(education.getSpecialization());

        return response;
    }
}

