package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.responses.ResumeGetResponse;
import com.androsov.itmo_blps.entities.resume.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ResumeToResumeGetResponseConverter implements Converter<Resume, ResumeGetResponse> {
    @Override
    public ResumeGetResponse convert(Resume source) {
        ResumeGetResponse response = new ResumeGetResponse();

        response.setUserId(source.getUser().getId());
        response.setResumeImageId(source.getResumeImage().getId());
        response.setName(source.getName());
        response.setSurname(source.getSurname());
        response.setPatronymic(source.getPatronymic());
        response.setDateOfBirth(source.getDateOfBirth());
        response.setCity(source.getCity());
        response.setMetroStation(source.getMetroStation());
        response.setPhoneNumber(source.getPhoneNumber());
        response.setEmail(source.getEmail());
        response.setDesiredPosition(source.getDesiredPosition());
        response.setSalary(source.getSalary());
        response.setEmployment(source.getEmployment());
        response.setSkills(source.getSkills());

        return response;
    }
}


