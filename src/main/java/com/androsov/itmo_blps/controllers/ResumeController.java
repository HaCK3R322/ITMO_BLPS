package com.androsov.itmo_blps.controllers;

import com.androsov.itmo_blps.annotations.FailOnGetParams;
import com.androsov.itmo_blps.dto.ResumeDto;
import com.androsov.itmo_blps.dto.converters.ResumeDtoToResumeConverter;
import com.androsov.itmo_blps.dto.converters.ResumeToResumeDtoConverter;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.Resume;
import com.androsov.itmo_blps.servicies.ImageService;
import com.androsov.itmo_blps.servicies.ResumeService;
import lombok.AllArgsConstructor;
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
    ResumeDtoToResumeConverter resumeDtoToResumeConverter;
    ResumeToResumeDtoConverter resumeToResumeDtoConverter;

    @PostMapping("/resume")
    public ResponseEntity<?> create(@Valid @RequestBody ResumeDto resumeDto,
                                    Principal principal) {
        if (resumeDto.getUsername() == null || !resumeDto.getUsername().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Current user has no access to create resume for user " + resumeDto.getUsername());
        }

        Resume resume = resumeDtoToResumeConverter.convert(resumeDto);

        Resume savedResume = resumeService.saveResume(resume);

        return new ResponseEntity<>(resumeToResumeDtoConverter.convert(savedResume), HttpStatus.CREATED);
    }

    @GetMapping("/resume")
    @FailOnGetParams
    public ResponseEntity<List<ResumeDto>> getAll(Principal principal, HttpServletRequest request) {
        List<Resume> resumes = resumeService.getAllForCurrentPrincipal(principal);

        List<ResumeDto> resumeDtos = resumeToResumeDtoConverter.convert(resumes);

        return new ResponseEntity<>(resumeDtos, HttpStatus.OK);
    }

    @PatchMapping("/resume/{resumeId}/attach/image/{imageId}")
    @FailOnGetParams
    public ResponseEntity<?> attachImage(@PathVariable Long resumeId, @PathVariable Long imageId, HttpServletRequest request) throws EntityNotFoundException {
        if (!resumeService.existsById(resumeId) || !imageService.existsById(imageId)) {
            return ResponseEntity.badRequest().body("Invalid resume or image ID (is null)");
        }

        Resume resume = resumeService.getById(resumeId);
        Image image = imageService.getImageById(imageId);

        resume.setImage(image);

        resume = resumeService.saveResume(resume);

        return new ResponseEntity<>(resumeToResumeDtoConverter.convert(resume), HttpStatus.OK);
    }
}
