package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.dto.VacancyDto;
import com.androsov.itmo_blps_lab1.dto.converters.VacancyDtoToVacancyConverter;
import com.androsov.itmo_blps_lab1.dto.converters.VacancyToVacancyDtoConverter;
import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.entities.ResumeVacancyLink;
import com.androsov.itmo_blps_lab1.repositories.ResumeRepository;
import com.androsov.itmo_blps_lab1.repositories.ResumeVacancyLinkRepository;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import com.androsov.itmo_blps_lab1.servicies.ResumeService;
import com.androsov.itmo_blps_lab1.servicies.ResumeVacancyLinkService;
import com.androsov.itmo_blps_lab1.servicies.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@ControllerAdvice
class VacancyControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ChangeSetPersister.NotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}


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
    public ResponseEntity<VacancyDto> createVacancy(@RequestBody VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyDtoToVacancyConverter.convert(vacancyDto);

        Vacancy savedVacancy = vacancyService.createVacancy(vacancy);

        return new ResponseEntity<>(vacancyToVacancyDtoConverter.convert(savedVacancy), HttpStatus.CREATED);
    }

//    @GetMapping("/vacancy/search")
//    public ResponseEntity<List<Vacancy>> search(@RequestParam String searchQuery) {
//        List<String> keywords = Arrays.asList(searchQuery.split("\\s+"));
//        List<Vacancy> vacancies = vacancyRepository.findAll();
//        List<Vacancy> matchingVacancies = new ArrayList<>();
//        for (Vacancy vacancy : vacancies) {
//            String name = vacancy.getName().toLowerCase();
//            boolean containsAllKeywords = true;
//            for (String keyword : keywords) {
//                if (!name.contains(keyword.toLowerCase())) {
//                    containsAllKeywords = false;
//                    break;
//                }
//            }
//            if (containsAllKeywords) {
//                matchingVacancies.add(vacancy);
//            }
//        }
//        return ResponseEntity.ok(matchingVacancies);
//    }

    @PostMapping("/vacancy/{vacancyId}/attach/resume/{resumeId}")
    public ResponseEntity<?> addResume(@PathVariable Long vacancyId, @PathVariable Long resumeId, Principal principal) throws ChangeSetPersister.NotFoundException {
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


}
