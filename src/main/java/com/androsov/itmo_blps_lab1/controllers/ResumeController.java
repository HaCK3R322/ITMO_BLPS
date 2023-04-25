package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.repositories.ResumeRepository;
import com.androsov.itmo_blps_lab1.entities.User;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ResumeController {
    UserRepository userRepository;
    ResumeRepository resumeRepository;

    @PostMapping("/resume/create")
    public ResponseEntity<Long> create(@RequestBody Resume resumeToCreate, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

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
    public ResponseEntity<List<Resume>> getAll(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        List<Resume> resumes = resumeRepository.getAllByUser(user);

        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }
}
