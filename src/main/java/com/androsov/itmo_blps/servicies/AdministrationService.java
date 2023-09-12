package com.androsov.itmo_blps.servicies;


import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.UserDeletingInfo;
import com.androsov.itmo_blps.model.entities.Image;
import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.model.entities.resume.Education;
import com.androsov.itmo_blps.model.entities.resume.Portfolio;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import com.androsov.itmo_blps.repositories.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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

    @Transactional
    public UserDeletingInfo deleteUserById(Long userId) throws AccessDeniedException {
        UserDeletingInfo userDeletingInfo = new UserDeletingInfo();

        User user = userService.getCurrentUser();
        userDeletingInfo.setDeletedUser(user);

        // additional hard-coded check
        if(!user.getRole().getName().equals("ROLE_ADMIN"))
            throw new AccessDeniedException("Only users with role ROLE_ADMIN has access to user deleting.");

        List<Image> deletedImages = imageRepository.deleteAllByUserId(userId);
        userDeletingInfo.setDeletedImages(deletedImages);

        List<Resume> userResumes = resumeRepository.getAllByUserId(userId);

        List<Education> deletedEducations = new ArrayList<>();
        userResumes.forEach(resume -> {
            deletedEducations.addAll(educationRepository.deleteAllByResume(resume));
        });
        userDeletingInfo.setDeletedEducations(deletedEducations);

        List<Portfolio> deletedPortfolios = new ArrayList<>();
        userResumes.forEach(resume -> {
            deletedPortfolios.addAll(portfolioRepository.deleteAllByResume(resume));
        });
        userDeletingInfo.setDeletedPortfolios(deletedPortfolios);

        List<WorkExperience> deletedWorkExperiences = new ArrayList<>();
        userResumes.forEach(resume -> {
            deletedWorkExperiences.addAll(workExperienceRepository.deleteAllByResume(resume));
        });
        userDeletingInfo.setDeletedWorkExperiences(deletedWorkExperiences);

        List<ResumeVacancyLink> deletedResumeVacancyLinks = new ArrayList<>();
        userResumes.forEach(resume -> {
            deletedResumeVacancyLinks.addAll(resumeVacancyLinkRepository.deleteAllByResume(resume));
        });
        userDeletingInfo.setDeletedResumeVacancyLinks(deletedResumeVacancyLinks);

        List<Resume> deletedResumes = resumeRepository.deleteAllByUserId(userId);
        userDeletingInfo.setDeletedResumes(deletedResumes);

        userRepository.deleteById(userId);

//        throw new EntityNotFoundException("Exception at the end of transaction!");

        return userDeletingInfo;
    }
}
