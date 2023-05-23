package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.annotations.FailOnGetParams;
import com.androsov.itmo_blps_lab1.dto.VacancyDto;
import com.androsov.itmo_blps_lab1.dto.VacancySearchParams;
import com.androsov.itmo_blps_lab1.dto.converters.VacancyDtoToVacancyConverter;
import com.androsov.itmo_blps_lab1.dto.converters.VacancyToVacancyDtoConverter;
import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.entities.ResumeVacancyLink;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import com.androsov.itmo_blps_lab1.servicies.ResumeService;
import com.androsov.itmo_blps_lab1.servicies.ResumeVacancyLinkService;
import com.androsov.itmo_blps_lab1.servicies.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;


@RestController
@CrossOrigin
@AllArgsConstructor
public class VacancyController {
    VacancyService vacancyService;

    ResumeService resumeService;
    ResumeVacancyLinkService resumeVacancyLinkService;

    VacancyDtoToVacancyConverter vacancyDtoToVacancyConverter;
    VacancyToVacancyDtoConverter vacancyToVacancyDtoConverter;

    @PostMapping(path = "/vacancy/create")
    @FailOnGetParams
    public ResponseEntity<VacancyDto> createVacancy(@RequestBody VacancyDto vacancyDto, HttpServletRequest request) {
        Vacancy vacancy = vacancyDtoToVacancyConverter.convert(vacancyDto);

        Vacancy savedVacancy = vacancyService.createVacancy(vacancy);

        return new ResponseEntity<>(vacancyToVacancyDtoConverter.convert(savedVacancy), HttpStatus.CREATED);
    }

    @PostMapping("/vacancy/{vacancyId}/attach/resume/{resumeId}")
    @FailOnGetParams
    public ResponseEntity<?> addResume(@PathVariable Long vacancyId, @PathVariable Long resumeId, Principal principal, HttpServletRequest request) throws EntityNotFoundException {
        if (!vacancyService.existsById(vacancyId) || !resumeService.existsById(resumeId)) {
            return ResponseEntity.badRequest().body("Invalid vacancy or resume ID (is null)");
        }

        Vacancy vacancy = vacancyService.getById(vacancyId);
        Resume resume = resumeService.getById(resumeId);

        if (!principal.getName().equals(resume.getUser().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot attach other users resume");
        }

        ResumeVacancyLink saved = resumeVacancyLinkService.create(resume, vacancy);
        return ResponseEntity.ok().body("Link created");
    }


    @GetMapping("/vacancy/search")
    public ResponseEntity<List<VacancyDto>> search(@RequestBody VacancySearchParams params, @RequestParam Integer page) {
        final int pagesize = 2; // hardcoded this xd

        List<Vacancy> vacancies = vacancyService.searchByParams(params, PageRequest.of(page, pagesize));
        List<VacancyDto> dtos = vacancyToVacancyDtoConverter.convert(vacancies);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}
