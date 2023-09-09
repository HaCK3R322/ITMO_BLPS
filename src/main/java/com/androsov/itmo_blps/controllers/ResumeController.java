package com.androsov.itmo_blps.controllers;

import com.androsov.itmo_blps.annotations.FailOnGetParams;
import com.androsov.itmo_blps.dto.EducationDto;
import com.androsov.itmo_blps.dto.ResumeDto;
import com.androsov.itmo_blps.dto.WorkExperienceDto;
import com.androsov.itmo_blps.dto.converters.*;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.resume.Education;
import com.androsov.itmo_blps.entities.resume.Resume;
import com.androsov.itmo_blps.entities.resume.WorkExperience;
import com.androsov.itmo_blps.repositories.EducationRepository;
import com.androsov.itmo_blps.repositories.WorkExperienceRepository;
import com.androsov.itmo_blps.servicies.EducationService;
import com.androsov.itmo_blps.servicies.ImageService;
import com.androsov.itmo_blps.servicies.ResumeService;
import com.androsov.itmo_blps.servicies.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@CrossOrigin
@AllArgsConstructor
@Validated
public class ResumeController {
    ResumeService resumeService;
    ImageService imageService;
    EducationService educationService;
    ResumeDtoToResumeConverter resumeDtoToResumeConverter;
    ResumeToResumeDtoConverter resumeToResumeDtoConverter;
    EducationDtoToEducationConverter educationDtoToEducationConverter;
    EducationToEducationDtoConverter educationToEducationDtoConverter;
    WorkExperienceRepository workExperienceRepository;
    WorkExperienceDtoToWorkExperienceConverter workExperienceDtoToWorkExperienceConverter;
    WorkExperienceToWorkExperienceDtoConverter workExperienceToWorkExperienceDtoConverter;

    @PostMapping("/resume")
    @FailOnGetParams
    public ResponseEntity<?> create(@Valid @RequestBody ResumeDto resumeDto,
                                    Principal principal, HttpServletRequest request) {

        // TODO: change this to auto-replace with username of current principal, if it worth it!
        if (resumeDto.getUsername() == null || !resumeDto.getUsername().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Current user has no rights to create resume for user " + resumeDto.getUsername());
        }

        Resume resume = resumeDtoToResumeConverter.convert(resumeDto);
        Resume savedResume = resumeService.saveResume(resume); // TODO: add method like 'Create from DTO' with access-denied throwing

        return new ResponseEntity<>(resumeToResumeDtoConverter.convert(savedResume), HttpStatus.CREATED);
    }

    @GetMapping("/resume")
    @FailOnGetParams
    public ResponseEntity<List<ResumeDto>> getAll(Principal principal, HttpServletRequest request) {
        List<Resume> resumes = resumeService.getAllForCurrentPrincipal(principal);
        List<ResumeDto> resumeDtos = resumeToResumeDtoConverter.convert(resumes);
        return new ResponseEntity<>(resumeDtos, HttpStatus.OK);
    }

    @GetMapping("/resume/{id}")
    @FailOnGetParams
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        Resume resume = resumeService.getById(id);
        ResumeDto resumeDto = resumeToResumeDtoConverter.convert(resume);
        return new ResponseEntity<>(resumeDto, HttpStatus.OK);
    }

    @PatchMapping("/resume/{resumeId}/attach/image/{imageId}")
    @FailOnGetParams
    public ResponseEntity<?> attachImage(@PathVariable Long resumeId, @PathVariable Long imageId, HttpServletRequest request) throws EntityNotFoundException {
        Resume resume = resumeService.attachImageById(resumeId, imageId);
        ResumeDto resumeDto = resumeToResumeDtoConverter.convert(resume);
        return new ResponseEntity<>(resumeDto, HttpStatus.OK);
    }

    @PostMapping("/resume/{resumeId}/education")
    @FailOnGetParams
    public ResponseEntity<?> addEducation(@PathVariable Long resumeId, @RequestBody EducationDto educationDto, HttpServletRequest request) throws EntityNotFoundException {
        Resume resume = resumeService.getById(resumeId);  // this will guarantee that resume exists and principal has access to it
        educationDto.setResumeId(resume.getId());

        Education education = educationDtoToEducationConverter.convert(educationDto);
        educationService.saveEducation(education);

        return ResponseEntity.status(HttpStatus.CREATED).body(educationToEducationDtoConverter.convert(education));
    }

    @GetMapping("/resume/{resumeId}/education")
    @FailOnGetParams
    public ResponseEntity<?> getAllEducations(@PathVariable Long resumeId, HttpServletRequest request) throws EntityNotFoundException {
        Resume resume = resumeService.getById(resumeId); // this will guarantee that resume exists and principal has access to it

        List<Education> educations = educationService.getAllByResume(resume);
        List<EducationDto> educationDtos = educationToEducationDtoConverter.convert(educations);

        return ResponseEntity.status(HttpStatus.CREATED).body(educationDtos);
    }


    // TODO : сделать все также как и с едукэйшон, но с w-e и portfolio



    @PostMapping("/resume/{resumeId}/work-experience")
    @FailOnGetParams
    public ResponseEntity<?> addWorkExperience(@PathVariable Long resumeId, @RequestBody WorkExperienceDto workExperienceDto, HttpServletRequest request) throws EntityNotFoundException {
        Resume resume = resumeService.getById(resumeId);

        if (!resumeService.currentPrincipalHasAccessToResumeById(resumeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Current user has no access to resume with id " + resumeId);
        }

        workExperienceDto.setResumeId(resumeId);
        WorkExperience workExperience = workExperienceRepository.save(workExperienceDtoToWorkExperienceConverter.convert(workExperienceDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(workExperienceToWorkExperienceDtoConverter.convert(workExperience));
    }

    @GetMapping("/resume/{resumeId}/work-experience")
    @FailOnGetParams
    public ResponseEntity<?> getAllWorkExperiences(@PathVariable Long resumeId, HttpServletRequest request) throws EntityNotFoundException {
        if (!resumeService.currentPrincipalHasAccessToResumeById(resumeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Current user has no access to resume with id " + resumeId);
        }

        List<WorkExperience> workExperiences = workExperienceRepository.getAllByResume(resumeService.getById(resumeId));
        List<WorkExperienceDto> workExperienceDtos = workExperienceToWorkExperienceDtoConverter.convert(workExperiences);

        return ResponseEntity.status(HttpStatus.OK).body(workExperienceDtos);
    }


}
