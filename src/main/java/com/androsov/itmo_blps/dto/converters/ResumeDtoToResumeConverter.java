package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.ResumeDto;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.Resume;
import com.androsov.itmo_blps.entities.User;
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
public class ResumeDtoToResumeConverter implements Converter<ResumeDto, Resume> {
    UserRepository userRepository;
    ImageService imageService;
    ResumeRepository resumeRepository;

    @Override
    public Resume convert(ResumeDto resumeDto) throws EntityNotFoundException {
        User user = userRepository.findByUsername(resumeDto.getUsername());

        Image image = null;
        if(resumeDto.getImageId() != null) {
            image = imageService.getImageById(resumeDto.getImageId());
        }

        if (resumeDto.getId() != null) {
            return resumeRepository.findById(resumeDto.getId()).orElseThrow(EntityNotFoundException::new);
        }

        return new Resume(null,
                user,
                resumeDto.getSpecialization(),
                resumeDto.getName(),
                resumeDto.getSurname(),
                resumeDto.getAge(),
                resumeDto.getStudyingDescription(),
                resumeDto.getJobsDescription(),
                image);
    }

    public List<Resume> convert(List<ResumeDto> resumes) {

        return resumes.stream()
                .map(this::convert)
                .toList();
    }
}
