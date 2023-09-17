package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.dto.messages.UserToCheckMessage;
import com.androsov.itmo_blps.dto.requests.ResumeCreateRequest;
import com.androsov.itmo_blps.model.entities.Image;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.repositories.ResumeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class ResumeService {
    private ResumeRepository resumeRepository;
    private ImageService imageService;
    private UserService userService;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;



    // ===============================================================================

    public Resume createFromRequest(ResumeCreateRequest resumeRequest) {
        //TODO: проверить на права на создание: создавать должен только пользователь с ролью "соискатель"

        User user = userService.getCurrentUser();

        Resume resume = new Resume();

        resume.setUserId(user.getId());
        resume.setName(resumeRequest.getName());
        resume.setSurname(resumeRequest.getSurname());
        resume.setPatronymic(resumeRequest.getPatronymic());
        resume.setDateOfBirth(resumeRequest.getDateOfBirth());
        resume.setCity(resumeRequest.getCity());
        resume.setMetroStation(resumeRequest.getMetroStation());
        resume.setPhoneNumber(resumeRequest.getPhoneNumber());
        resume.setEmail(resumeRequest.getEmail());
        resume.setDesiredPosition(resumeRequest.getDesiredPosition());
        resume.setSalary(resumeRequest.getSalary());
        resume.setEmployment(resumeRequest.getEmployment());
        resume.setSkills(resumeRequest.getSkills());

        return resumeRepository.save(resume);
    }

    public void sendUserToCheck(Resume resume) {
        UserToCheckMessage userToCheckMessage = new UserToCheckMessage();
        userToCheckMessage.setUserId(userService.getCurrentUser().getId());
        userToCheckMessage.setName(resume.getName());
        userToCheckMessage.setSurname(resume.getSurname());
        userToCheckMessage.setPatronymic(resume.getPatronymic());
        userToCheckMessage.setDateOfBirth(resume.getDateOfBirth());
        userToCheckMessage.setCity(resume.getCity());

        try {
            String message = objectMapper.writeValueAsString(userToCheckMessage);

            kafkaProducerService.sendMessage("user-check", message);
        } catch (JsonProcessingException e) {
            Logger.getLogger(ResumeService.class.getName())
                    .log(Level.WARNING, "Cant send user to check: " + e.getMessage());
        }
    }

    public List<Resume> getAllForCurrentPrincipal() {
        User user = userService.getCurrentUser();
        return resumeRepository.getAllByUserId(user.getId());
    }

    public List<Resume> getAllByUserId(Long userId) {
        return resumeRepository.getAllByUserId(userId);
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
        String resumeUsername = userService.getById(resume.getUserId()).getUsername();
        String currentUserUsername = userService.getCurrentUser().getUsername();

        return currentUserUsername.equals(resumeUsername);
    }
}
