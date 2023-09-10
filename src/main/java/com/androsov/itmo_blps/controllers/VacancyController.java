package com.androsov.itmo_blps.controllers;

import com.androsov.itmo_blps.annotations.FailOnGetParams;
import com.androsov.itmo_blps.dto.requests.VacancySearchParams;
import com.androsov.itmo_blps.dto.requests.VacancyCreateRequest;
import com.androsov.itmo_blps.dto.responses.VacancyGetResponse;
import com.androsov.itmo_blps.model.entities.Vacancy;
import com.androsov.itmo_blps.servicies.ResumeService;
import com.androsov.itmo_blps.servicies.ResumeVacancyLinkService;
import com.androsov.itmo_blps.servicies.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin
@AllArgsConstructor
public class VacancyController {
    VacancyService vacancyService;

    ResumeService resumeService;
    ResumeVacancyLinkService resumeVacancyLinkService;
    ConversionService conversionService;


    @PostMapping(path = "/vacancy")
    @PreAuthorize("hasAuthority('VACANCY_CREATE')")
    @FailOnGetParams
    public ResponseEntity<?> createVacancy(@RequestBody @Valid VacancyCreateRequest request,
                                                    HttpServletRequest httpServletRequest) {
        Vacancy vacancy = vacancyService.createFromRequest(request);
        return new ResponseEntity<>(conversionService.convert(vacancy, VacancyGetResponse.class), HttpStatus.CREATED);
    }

    @GetMapping("/vacancy/search")
    @PreAuthorize("hasAuthority('VACANCY_VIEW')")
    public ResponseEntity<?> search(@RequestBody VacancySearchParams params, @RequestParam Integer page) {
        final int pagesize = 2; // hardcoded this xd

        List<Vacancy> vacancies = vacancyService.searchByParams(params, PageRequest.of(page, pagesize));

        List<VacancyGetResponse> vacanciesGetResponse = vacancies.stream()
                .map(vacancy -> conversionService.convert(vacancy, VacancyGetResponse.class))
                .toList();

        return ResponseEntity.ok().body(vacanciesGetResponse);
    }

}
