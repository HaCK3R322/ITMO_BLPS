package com.androsov.itmo_blps_lab1.dto.converters;

import com.androsov.itmo_blps_lab1.dto.ResumeDto;
import com.androsov.itmo_blps_lab1.model.entities.Resume;
import com.androsov.itmo_blps_lab1.model.entities.User;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.List;


@AllArgsConstructor
public class ResumeDtoToResumeConverter implements Converter<ResumeDto, Resume> {
    UserRepository userRepository;

    @Override
    public Resume convert(ResumeDto resumeDto) {
        User user = userRepository.findByUsername(resumeDto.getUsername());

        return new Resume(resumeDto.getId(),
                user,
                resumeDto.getSpecialization(),
                resumeDto.getName(),
                resumeDto.getSurname(),
                resumeDto.getAge(),
                resumeDto.getStudyingDescription(),
                resumeDto.getJobsDescription());
    }

    public List<Resume> convert(List<ResumeDto> resumes) {

        return resumes.stream()
                .map(this::convert)
                .toList();
    }
}
