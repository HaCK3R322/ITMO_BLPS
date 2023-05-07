package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.dto.ResumeDto;
import com.androsov.itmo_blps_lab1.dto.converters.ResumeDtoToResumeConverter;
import com.androsov.itmo_blps_lab1.dto.converters.ResumeToResumeDtoConverter;
import com.androsov.itmo_blps_lab1.entities.Image;
import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.servicies.ImageService;
import com.androsov.itmo_blps_lab1.servicies.ResumeService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@ControllerAdvice
class ResumeControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
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
@Validated
public class ResumeController {

    ResumeService resumeService;
    ImageService imageService;
    ResumeDtoToResumeConverter resumeDtoToResumeConverter;
    ResumeToResumeDtoConverter resumeToResumeDtoConverter;

    @PostMapping("/resume/create")
    public ResponseEntity<ResumeDto> create(@Valid @RequestBody ResumeDto resumeDto, Principal principal) {
        Resume resume = resumeDtoToResumeConverter.convert(resumeDto);

        Resume savedResume = resumeService.saveResume(resume);

        return new ResponseEntity<>(resumeToResumeDtoConverter.convert(savedResume), HttpStatus.CREATED);
    }

    @GetMapping("/resume/get/all")
    public ResponseEntity<List<ResumeDto>> getAll(Principal principal) {
        List<Resume> resumes = resumeService.getAllForCurrentPrincipal(principal);

        List<ResumeDto> resumeDtos = resumeToResumeDtoConverter.convert(resumes);

        return new ResponseEntity<>(resumeDtos, HttpStatus.OK);
    }

    //TODO: move logic to ResumeService
    @PatchMapping("/resume/{resumeId}/attach/image/{imageId}")
    public ResponseEntity<?> attachImage(@PathVariable Long resumeId, @PathVariable Long imageId) throws ChangeSetPersister.NotFoundException {
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
