package com.androsov.itmo_blps.servicies;


import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
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

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AdministrationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private WorkExperienceRepository workExperienceRepository;
    @Autowired
    private ResumeVacancyLinkRepository resumeVacancyLinkRepository;

    @Autowired UserService userService;
    @Transactional
    public void deleteUserById(Long userId) throws AccessDeniedException {
        User user = userService.getCurrentUser();

//        if(!user.getRole().getName().equals("ROLE_ADMIN"))
//            throw new AccessDeniedException("Only users with role Admin can manage other users");

        imageRepository.deleteAllByUserId(userId);

        List<Resume> userResumes = resumeRepository.getAllByUserId(userId);

        userResumes.forEach(resume -> {
            educationRepository.deleteAllByResume(resume);
        });

        userResumes.forEach(resume -> {
            portfolioRepository.deleteAllByResume(resume);
        });

        userResumes.forEach(resume -> {
            workExperienceRepository.deleteAllByResume(resume);
        });

        userResumes.forEach(resume -> {
            resumeVacancyLinkRepository.deleteAllByResume(resume);
        });

        resumeRepository.deleteAllByUserId(userId);

        userRepository.deleteById(userId);
    }
}
