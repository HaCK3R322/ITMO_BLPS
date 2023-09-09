package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.resume.Education;
import com.androsov.itmo_blps.entities.resume.Resume;
import com.androsov.itmo_blps.entities.User;
import com.androsov.itmo_blps.repositories.ResumeRepository;
import com.androsov.itmo_blps.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResumeService {
    ResumeRepository resumeRepository;
    UserRepository userRepository;
    ImageService imageService;

    public Resume saveResume(Resume resume) {
        return resumeRepository.save(resume);
    }

    public List<Resume> getAllByUser(User user) {
        return resumeRepository.getAllByUser(user);
    }

    public List<Resume> getAllForCurrentPrincipal(Principal principal) {
        String username = principal.getName();
        User user = userRepository.getByUsername(username);

        return resumeRepository.getAllByUser(user);
    }

    public Resume getById(Long id) throws EntityNotFoundException, AccessDeniedException {
        Optional<Resume> resumeOptional = resumeRepository.findById(id);

        if(resumeOptional.isEmpty())
            throw new EntityNotFoundException("Resume with id " + id + " not found!");

        if(!currentPrincipalHasAccessToResume(resumeOptional.get()))
            throw new AccessDeniedException("User with username "
                    + SecurityContextHolder.getContext().getAuthentication().getName()
                    + " has no access to resume with id " + id);

        return resumeOptional.get();
    }

    public Resume attachImageById(Long resumeId, Long imageId) {
        Resume resume = this.getById(resumeId); // we interested in no-change of resume except image
        Image image = imageService.getById(imageId);

        resume.setResumeImage(image);
        resumeRepository.save(resume);

        return resume;
    }

    public boolean existsById(Long id) {
        return resumeRepository.existsById(id);
    }
    public boolean exists(Resume resume) {
        return existsById(resume.getId());
    }

    public boolean currentPrincipalHasAccessToResume(Resume resume) throws EntityNotFoundException { //TODO: not role-based yet
        String resumeUsername = resume.getUser().getUsername();
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();

        return principalName.equals(resumeUsername);
    }

}
