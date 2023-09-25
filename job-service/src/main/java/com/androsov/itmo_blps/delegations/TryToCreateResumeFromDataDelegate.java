package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.dto.requests.ResumeCreateRequest;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.servicies.ResumeService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.persistence.NamedEntityGraph;
import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;

@Named
@Component
@RequiredArgsConstructor
public class TryToCreateResumeFromDataDelegate implements JavaDelegate {
    private final ResumeService resumeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String name = (String) execution.getVariable("name");
        String surname = (String) execution.getVariable("surname");
        String patronymic = (String) execution.getVariable("patronymic");
        LocalDate dateOfBirth = LocalDate.parse(((String) execution.getVariable("dateOfBirth")));
        String city = (String) execution.getVariable("city");
        String metroStation = (String) execution.getVariable("metroStation");
        String phoneNumber = (String) execution.getVariable("phoneNumber");
        String email = (String) execution.getVariable("email");
        String desiredPosition = (String) execution.getVariable("desiredPosition");
        Double salary = Double.parseDouble((String) execution.getVariable("salary"));
        String employment = (String) execution.getVariable("employment");
        String skills = (String) execution.getVariable("skills");

        ResumeCreateRequest request = new ResumeCreateRequest(name,
                surname,
                patronymic,
                dateOfBirth,
                city,
                metroStation,
                phoneNumber,
                email,
                desiredPosition,
                salary,
                employment,
                skills
        );

        try {
            validateRequest(request);
            Resume resume = resumeService.createFromRequest(request);
            resumeService.sendUserToCheck(resume);
            execution.setVariable("createdResumeId", resume.getId());
        } catch (ValidationException ex) {
            execution.setVariable("RESUME_CREATION_ERROR", ex.getMessage());
            throw new BpmnError("RESUME_CREATION_ERROR", ex.getMessage());
        } catch (Exception ex) {
            execution.setVariable("RESUME_CREATION_ERROR", "An error occurred while creating the resume.");
            throw new BpmnError("RESUME_CREATION_ERROR", "An error occurred while creating the resume.");
        }
    }

    private void validateRequest(ResumeCreateRequest request) throws ValidationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ResumeCreateRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<ResumeCreateRequest> violation : violations) {
                errorMessages.append(violation.getMessage()).append("; ");
            }
            throw new ValidationException(errorMessages.toString());
        }
    }
}
