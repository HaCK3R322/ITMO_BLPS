package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.EducationDto;
import com.androsov.itmo_blps.dto.ResumeDto;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.User;
import com.androsov.itmo_blps.entities.resume.Education;
import com.androsov.itmo_blps.entities.resume.Resume;
import com.androsov.itmo_blps.repositories.EducationRepository;
import com.androsov.itmo_blps.repositories.ResumeRepository;
import com.androsov.itmo_blps.repositories.UserRepository;
import com.androsov.itmo_blps.servicies.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@AllArgsConstructor
public class EducationDtoToEducationConverter implements Converter<EducationDto, Education> {
    EducationRepository educationRepository;
    ResumeRepository resumeRepository;

    @Override
    public Education convert(EducationDto educationDto) throws EntityNotFoundException {
        if (educationDto.getId() != null) { // if we work with some created resume
            return educationRepository.findById(educationDto.getId()).orElseThrow(EntityNotFoundException::new);
        }

        Resume resume = resumeRepository.findById(educationDto.getResumeId()).orElseThrow(EntityNotFoundException::new);

        Education education = new Education();

        education.setResume(resume);
        education.setLevel(educationDto.getLevel());
        education.setEndDate(educationDto.getEndDate());
        education.setUniversityName(educationDto.getUniversityName());
        education.setFaculty(educationDto.getFaculty());
        education.setSpecialization(educationDto.getSpecialization());

        return education;
    }

    public List<Education> convert(List<EducationDto> educationDtos) {

        return educationDtos.stream()
                .map(this::convert)
                .toList();
    }
}