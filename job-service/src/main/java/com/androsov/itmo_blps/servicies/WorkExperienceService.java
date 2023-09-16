package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.dto.requests.WorkExperienceCreateRequest;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import com.androsov.itmo_blps.repositories.WorkExperienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkExperienceService {
    private WorkExperienceRepository workExperienceRepository;
    private ResumeService resumeService;

    public WorkExperience createFromRequest(Resume resume, WorkExperienceCreateRequest request) {
        WorkExperience workExperience = new WorkExperience();

        workExperience.setResume(resume);

        workExperience.setPosition(request.getPosition());
        workExperience.setOrganizationName(request.getOrganizationName());
        workExperience.setWebsite(request.getWebsite());
        workExperience.setCity(request.getCity());
        workExperience.setCompanyActivity(request.getCompanyActivity());
        workExperience.setResponsibilities(request.getResponsibilities());
        workExperience.setAchievements(request.getAchievements());
        workExperience.setStartDate(request.getStartDate());
        workExperience.setEndDate(request.getEndDate());

        return workExperienceRepository.save(workExperience);
    }

    public List<WorkExperience> getAllByResume(Resume resume) {
        return workExperienceRepository.getAllByResume(resume);
    }
}
