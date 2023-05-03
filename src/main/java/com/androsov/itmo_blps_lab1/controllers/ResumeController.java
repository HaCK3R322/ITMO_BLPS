package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.dto.ResumeDto;
import com.androsov.itmo_blps_lab1.dto.converters.ResumeDtoToResumeConverter;
import com.androsov.itmo_blps_lab1.dto.converters.ResumeToResumeDtoConverter;
import com.androsov.itmo_blps_lab1.model.entities.Resume;
import com.androsov.itmo_blps_lab1.servicies.ResumeService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@AllArgsConstructor
@Validated
public class ResumeController {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class.getName());

    ResumeService resumeService;
    ResumeDtoToResumeConverter resumeDtoToResumeConverter;
    ResumeToResumeDtoConverter resumeToResumeDtoConverter;

    @PostMapping("/resume/create")
    public ResponseEntity<ResumeDto> create(@Valid @RequestBody ResumeDto resumeDto, Principal principal) {

        Resume resume = resumeDtoToResumeConverter.convert(resumeDto);
        Resume savedResume = resumeService.createResume(resume);

        return new ResponseEntity<>(resumeToResumeDtoConverter.convert(savedResume), HttpStatus.CREATED);
    }

    @GetMapping("/resume/get/all")
    public ResponseEntity<List<ResumeDto>> getAll(Principal principal) {
        List<Resume> resumes = resumeService.getAllForCurrentPrincipal(principal);
        List<ResumeDto> resumeDtos = resumeToResumeDtoConverter.convert(resumes);

        return new ResponseEntity<>(resumeDtos, HttpStatus.OK);
    }
}
