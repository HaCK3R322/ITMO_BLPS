package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.dto.requests.EducationCreateRequest;
import com.androsov.itmo_blps.model.entities.resume.Education;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.repositories.EducationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EducationService {
    EducationRepository educationRepository;
    ResumeService resumeService;

    public Education createFromRequest(Resume resume, EducationCreateRequest request) {
        Education education = new Education();

        education.setResume(resume);

        education.setLevel(request.getLevel());
        education.setForm(request.getForm());
        education.setEndDate(request.getEndDate());
        education.setUniversityName(request.getUniversityName());
        education.setFaculty(request.getFaculty());
        education.setSpecialization(request.getSpecialization());

        return educationRepository.save(education);
    }


    public List<Education> getAllByResume(Resume resume) {
        return educationRepository.getAllByResume(resume);
    }
}
