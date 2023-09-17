package com.androsov.itmo_blps.controllers;

import com.androsov.itmo_blps.annotations.FailOnGetParams;
import com.androsov.itmo_blps.dto.requests.EducationCreateRequest;
import com.androsov.itmo_blps.dto.requests.PortfolioCreateRequest;
import com.androsov.itmo_blps.dto.requests.ResumeCreateRequest;
import com.androsov.itmo_blps.dto.requests.WorkExperienceCreateRequest;
import com.androsov.itmo_blps.dto.responses.*;
import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.model.entities.Vacancy;
import com.androsov.itmo_blps.model.entities.resume.Education;
import com.androsov.itmo_blps.model.entities.resume.Portfolio;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import com.androsov.itmo_blps.servicies.*;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@AllArgsConstructor
@Validated
public class ResumeController {
    private final ResumeService resumeService;
    private final EducationService educationService;
    private final ConversionService conversionService;
    private final WorkExperienceService workExperienceService;
    private final PortfolioService portfolioService;
    private final VacancyService vacancyService;
    private final ResumeVacancyLinkService resumeVacancyLinkService;

    @PostMapping("/resume")
    @PreAuthorize("hasAuthority('RESUME_CREATE')")
    @FailOnGetParams
    public ResponseEntity<?> create(@Valid @RequestBody ResumeCreateRequest resumeRequest, HttpServletRequest httpServletRequest) {
        Resume savedResume = resumeService.createFromRequest(resumeRequest);

        // MODERATION
        resumeService.sendUserToCheck(savedResume);
        // ==========

        return new ResponseEntity<>(conversionService.convert(savedResume, ResumeGetResponse.class), HttpStatus.CREATED);
    }

    @GetMapping("/resume")
    @PreAuthorize("hasAuthority('RESUME_VIEW')")
    @FailOnGetParams
    public ResponseEntity<List<ResumeGetResponse>> getAll(HttpServletRequest request) {
        List<Resume> resumes = resumeService.getAllForCurrentPrincipal();

        List<ResumeGetResponse> resumeGetResponseList = resumes.stream()
                .map(resume -> conversionService.convert(resume, ResumeGetResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resumeGetResponseList, HttpStatus.OK);
    }

    @GetMapping("/resume/{id}")
    @PreAuthorize("hasAuthority('RESUME_VIEW')")
    @FailOnGetParams
    public ResponseEntity<?> getById(@PathVariable Long id, HttpServletRequest request) {
        Resume resume = resumeService.getById(id);
        ResumeGetResponse response = conversionService.convert(resume, ResumeGetResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/resume/{resumeId}/attach/image/{imageId}")
    @PreAuthorize("hasAuthority('RESUME_MANIPULATION')")
    @FailOnGetParams
    public ResponseEntity<?> attachImage(@PathVariable Long resumeId, @PathVariable Long imageId, HttpServletRequest request) throws EntityNotFoundException {
        Resume resume = resumeService.attachImageById(resumeId, imageId);
        ResumeGetResponse response = conversionService.convert(resume, ResumeGetResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resume/{resumeId}/education")
    @PreAuthorize("hasAuthority('RESUME_MANIPULATION')")
    @FailOnGetParams
    public ResponseEntity<?> addEducation(@PathVariable Long resumeId, @RequestBody @Valid EducationCreateRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException {
        Resume resume = resumeService.getById(resumeId);  // this will guarantee that resume exists and principal has access to it

        Education education = educationService.createFromRequest(resume, request);
        EducationGetResponse response = conversionService.convert(education, EducationGetResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/resume/{resumeId}/education")
    @PreAuthorize("hasAuthority('RESUME_VIEW')")
    @FailOnGetParams
    public ResponseEntity<?> getAllEducations(@PathVariable Long resumeId, HttpServletRequest request) throws EntityNotFoundException {
        Resume resume = resumeService.getById(resumeId); // this will guarantee that resume exists and principal has access to it

        List<Education> educations = educationService.getAllByResume(resume);
        List<EducationGetResponse> educationsResponse = educations.stream()
                .map(education -> conversionService.convert(education, EducationGetResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(educationsResponse);
    }


    // WORK EXPERIENCE

    @PostMapping("/resume/{resumeId}/work-experience")
    @PreAuthorize("hasAuthority('RESUME_MANIPULATION')")
    @FailOnGetParams
    public ResponseEntity<?> addWorkExperience(@PathVariable Long resumeId, @RequestBody @Valid WorkExperienceCreateRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException {
        Resume resume = resumeService.getById(resumeId);

        WorkExperience workExperience = workExperienceService.createFromRequest(resume, request); // Create WorkExperience the right way
        WorkExperienceGetResponse response = conversionService.convert(workExperience, WorkExperienceGetResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/resume/{resumeId}/work-experience")
    @PreAuthorize("hasAuthority('RESUME_VIEW')")
    @FailOnGetParams
    public ResponseEntity<?> getAllWorkExperiences(@PathVariable Long resumeId, HttpServletRequest request) throws EntityNotFoundException {
        Resume resume = resumeService.getById(resumeId);

        List<WorkExperience> workExperiences = workExperienceService.getAllByResume(resume);

        List<WorkExperienceGetResponse> workExperienceResponses = workExperiences.stream()
                .map(workExperience -> conversionService.convert(workExperience, WorkExperienceGetResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(workExperienceResponses);
    }

    // PORTFOLIO

    @PostMapping("/resume/{resumeId}/portfolio")
    @PreAuthorize("hasAuthority('RESUME_MANIPULATION')")
    @FailOnGetParams
    public ResponseEntity<?> addPortfolio(
            @PathVariable Long resumeId,
            @RequestBody @Valid PortfolioCreateRequest request,
            HttpServletRequest httpServletRequest) throws EntityNotFoundException {

        Resume resume = resumeService.getById(resumeId);
        Portfolio portfolio = portfolioService.createFromRequest(resume, request);
        PortfolioGetResponse response = conversionService.convert(portfolio, PortfolioGetResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/resume/{resumeId}/portfolio")
    @PreAuthorize("hasAuthority('RESUME_VIEW')")
    @FailOnGetParams
    public ResponseEntity<?> getAllPortfolios(@PathVariable Long resumeId, HttpServletRequest request) throws EntityNotFoundException {
        Resume resume = resumeService.getById(resumeId); // this will guarantee that resume exists and principal has access to it

        List<Portfolio> portfolios = portfolioService.getAllByResume(resume);

        List<PortfolioGetResponse> portfolioResponses = portfolios.stream()
                .map(portfolio -> conversionService.convert(portfolio, PortfolioGetResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(portfolioResponses);
    }

    @PostMapping("/resume/{resumeId}/attach-to-vacancy/{vacancyId}")
    @PreAuthorize("hasAuthority('RESUME_MANIPULATION')")
    @FailOnGetParams
    public ResponseEntity<?> attachToVacancy(@PathVariable Long resumeId, @PathVariable Long vacancyId, HttpServletRequest request) throws EntityNotFoundException {
        Resume resume = resumeService.getById(resumeId); // this will guarantee that resume exists and principal has access to it
        Vacancy vacancy = vacancyService.getById(vacancyId);

        ResumeVacancyLink saved = resumeVacancyLinkService.create(resume, vacancy);

        return ResponseEntity.ok().body(conversionService.convert(saved, ResumeVacancyLinkGetResponse.class));
    }
}
