package com.androsov.itmo_blps_lab1.servicies;

import com.androsov.itmo_blps_lab1.model.entities.Resume;
import com.androsov.itmo_blps_lab1.model.entities.User;
import com.androsov.itmo_blps_lab1.repositories.ResumeRepository;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class ResumeService {
    ResumeRepository resumeRepository;
    UserRepository userRepository;

    public Resume createResume(Resume resume) {
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

}
