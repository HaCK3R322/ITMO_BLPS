package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.entities.resume.Education;
import com.androsov.itmo_blps.entities.resume.Resume;
import com.androsov.itmo_blps.repositories.EducationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EducationService {
    EducationRepository educationRepository;
    ResumeService resumeService;

    public Education saveEducation(Education education) {
        return educationRepository.save(education);
    }

    public List<Education> getAllByResume(Resume resume) {
        return educationRepository.getAllByResume(resume);
    }
}
