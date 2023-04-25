package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.entities.ResumeVacancyLink;
import com.androsov.itmo_blps_lab1.repositories.ResumeRepository;
import com.androsov.itmo_blps_lab1.repositories.ResumeVacancyLinkRepository;
import com.androsov.itmo_blps_lab1.repositories.VacancyRepository;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class VacancyController {
    VacancyRepository vacancyRepository;
    ResumeRepository resumeRepository;
    ResumeVacancyLinkRepository resumeVacancyLinkRepository;

    @GetMapping("/vacancy/search")
    public ResponseEntity<List<Vacancy>> search(@RequestParam String searchQuery) {
        List<String> keywords = Arrays.asList(searchQuery.split("\\s+"));
        List<Vacancy> vacancies = vacancyRepository.findAll();
        List<Vacancy> matchingVacancies = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            String name = vacancy.getName().toLowerCase();
            boolean containsAllKeywords = true;
            for (String keyword : keywords) {
                if (!name.contains(keyword.toLowerCase())) {
                    containsAllKeywords = false;
                    break;
                }
            }
            if (containsAllKeywords) {
                matchingVacancies.add(vacancy);
            }
        }
        return ResponseEntity.ok(matchingVacancies);
    }

    @PostMapping("/vacancy/addresume")
    public ResponseEntity<String> addResume(@RequestParam Long vacancyId, @RequestParam Long resumeId) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElse(null);
        Resume resume = resumeRepository.findById(resumeId).orElse(null);
        if (vacancy == null || resume == null) {
            return ResponseEntity.badRequest().body("Invalid vacancy or resume ID");
        }
        ResumeVacancyLink link = new ResumeVacancyLink(null, resume, vacancy);
        resumeVacancyLinkRepository.save(link);
        return ResponseEntity.ok("ResumeVacancyLink created successfully");
    }


}
