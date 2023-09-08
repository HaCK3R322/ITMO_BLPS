package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.ResumeDto;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.resume.Resume;
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
        if (resumeDto.getId() != null) { // if we work with some created resume
            return resumeRepository.findById(resumeDto.getId()).orElseThrow(EntityNotFoundException::new);
        }

        User user = userRepository.findById(resumeDto.getUserId());

        Resume resume = new Resume();
        resume.setUser(user);

        if(resumeDto.getImageId() != null) {
            Image image = imageService.getImageById(resumeDto.getImageId());
            resume.setResumeImage(image);
        }

        resume.setName(resumeDto.getName());
        resume.setSurname(resumeDto.getSurname());
        resume.setPatronymic(resumeDto.getPatronymic());
        resume.setDateOfBirth(resumeDto.getDateOfBirth());
        resume.setCity(resumeDto.getCity());
        resume.setMetroStation(resumeDto.getMetroStation());
        resume.setPhoneNumber(resumeDto.getPhoneNumber());
        resume.setEmail(resumeDto.getEmail());
        resume.setDesiredPosition(resumeDto.getDesiredPosition());
        resume.setSalary(resumeDto.getSalary());
        resume.setEmployment(resumeDto.getEmployment());
        resume.setSkills(resumeDto.getSkills());

        return resume;
    }

    public List<Resume> convert(List<ResumeDto> resumes) {

        return resumes.stream()
                .map(this::convert)
                .toList();
    }
}
