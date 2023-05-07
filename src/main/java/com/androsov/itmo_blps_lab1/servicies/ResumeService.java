package com.androsov.itmo_blps_lab1.servicies;

import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.entities.User;
import com.androsov.itmo_blps_lab1.repositories.ResumeRepository;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

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

    public Resume getById(Long id) throws ChangeSetPersister.NotFoundException {
        return resumeRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public boolean exists(Resume resume) {
        if(resume == null || resume.getId() == null) return false;

        return resumeRepository.existsById(resume.getId());
    }
    public boolean existsById(Long id) {
        return resumeRepository.existsById(id);
    }


}
