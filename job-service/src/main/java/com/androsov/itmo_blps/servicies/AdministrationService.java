package com.androsov.itmo_blps.servicies;


import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.WorkerUserDeletingInfo;
import com.androsov.itmo_blps.model.entities.Image;
import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.model.entities.resume.Education;
import com.androsov.itmo_blps.model.entities.resume.Portfolio;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import com.androsov.itmo_blps.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdministrationService {
    private UserRepository userRepository;
    private ImageRepository imageRepository;
    private ResumeRepository resumeRepository;
    private EducationRepository educationRepository;
    private PortfolioRepository portfolioRepository;
    private WorkExperienceRepository workExperienceRepository;
    private ResumeVacancyLinkRepository resumeVacancyLinkRepository;

    private UserService userService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public WorkerUserDeletingInfo deleteWorkerUserById(Long userId) throws AccessDeniedException {
        WorkerUserDeletingInfo workerUserDeletingInfo = new WorkerUserDeletingInfo();

        User user = userService.getCurrentUser();
        workerUserDeletingInfo.setDeletedUser(user);

        // additional hard-coded check
        if(!user.getRole().getName().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Only users with role ROLE_ADMIN has access to user deleting.");

        List<Image> deletedImages = imageRepository.deleteAllByUserId(userId);
        workerUserDeletingInfo.setDeletedImages(deletedImages);

        List<Resume> userResumes = resumeRepository.getAllByUserId(userId);

        List<Education> deletedEducations = new ArrayList<>();
        userResumes.forEach(resume -> {
            deletedEducations.addAll(educationRepository.deleteAllByResume(resume));
        });
        workerUserDeletingInfo.setDeletedEducations(deletedEducations);

        List<Portfolio> deletedPortfolios = new ArrayList<>();
        userResumes.forEach(resume -> {
            deletedPortfolios.addAll(portfolioRepository.deleteAllByResume(resume));
        });
        workerUserDeletingInfo.setDeletedPortfolios(deletedPortfolios);

        List<WorkExperience> deletedWorkExperiences = new ArrayList<>();
        userResumes.forEach(resume -> {
            deletedWorkExperiences.addAll(workExperienceRepository.deleteAllByResume(resume));
        });
        workerUserDeletingInfo.setDeletedWorkExperiences(deletedWorkExperiences);

        List<ResumeVacancyLink> deletedResumeVacancyLinks = new ArrayList<>();
        userResumes.forEach(resume -> {
            deletedResumeVacancyLinks.addAll(resumeVacancyLinkRepository.deleteAllByResume(resume));
        });
        workerUserDeletingInfo.setDeletedResumeVacancyLinks(deletedResumeVacancyLinks);

        List<Resume> deletedResumes = resumeRepository.deleteAllByUserId(userId);
        workerUserDeletingInfo.setDeletedResumes(deletedResumes);

        userRepository.deleteById(userId);

        return workerUserDeletingInfo;
    }
}
