package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.entities.resume.Resume;
import com.androsov.itmo_blps.entities.User;
import com.androsov.itmo_blps.repositories.ResumeRepository;
import com.androsov.itmo_blps.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class ResumeService {
    ResumeRepository resumeRepository;
    UserRepository userRepository;

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

    // TODO: work with optional - need to throw normal Exceptions like access denied or what entity not found
    public Resume getById(Long id) throws EntityNotFoundException {
        return resumeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public boolean exists(Resume resume) {
        if(resume == null || resume.getId() == null) return false;

        return resumeRepository.existsById(resume.getId());
    }
    public boolean existsById(Long id) {
        return resumeRepository.existsById(id);
    }


}
