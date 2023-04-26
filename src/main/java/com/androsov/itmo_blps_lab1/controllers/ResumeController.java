package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.controllers.dto.ResumeDto;
import com.androsov.itmo_blps_lab1.model.entities.Resume;
import com.androsov.itmo_blps_lab1.repositories.ResumeRepository;
import com.androsov.itmo_blps_lab1.model.entities.User;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor
@Validated
public class ResumeController {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class.getName());


    UserRepository userRepository;
    ResumeRepository resumeRepository;

    private ConversionService conversionService;

    @PostMapping("/resume/create")
    public ResponseEntity<Long> create(@Valid @RequestBody Resume resumeToCreate, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        LOGGER.log(Level.INFO, resumeToCreate.toString());

        Resume resume = new Resume(null,
                user,
                resumeToCreate.getSpecialization(),
                resumeToCreate.getName(),
                resumeToCreate.getSurname(),
                resumeToCreate.getAge(),
                resumeToCreate.getStudyingDescription(),
                resumeToCreate.getJobsDescription());

        Resume savedResume = resumeRepository.save(resume);

        return new ResponseEntity<>(savedResume.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/resume/getAll")
    public ResponseEntity<List<ResumeDto>> getAll(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        List<Resume> resumes = resumeRepository.getAllByUser(user);
        List<ResumeDto> resumeDtos = resumes.stream()
                .map(e -> conversionService.convert(e, ResumeDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resumeDtos, HttpStatus.OK);
    }
}
