package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.dto.requests.VacancyCreateRequest;
import com.androsov.itmo_blps.model.entities.Vacancy;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

@Named
@Component
@RequiredArgsConstructor
public class ValidateVacancyDataDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String name = (String) execution.getVariable("name");
        String description = (String) execution.getVariable("description");
        String city = (String) execution.getVariable("city");
        String address = (String) execution.getVariable("address");
        String requiredWorkExperience = (String) execution.getVariable("requiredWorkExperience");
        String responsibilities = (String) execution.getVariable("responsibilities");
        String requirements = (String) execution.getVariable("requirements");
        String conditions = (String) execution.getVariable("conditions");
        Integer salaryFrom = Integer.parseInt((String) execution.getVariable("salaryFrom"));
        Integer salaryTo = Integer.parseInt((String) execution.getVariable("salaryTo"));

        VacancyCreateRequest request = new VacancyCreateRequest();
        request.setName(name);
        request.setDescription(description);
        request.setCity(city);
        request.setAddress(address);
        request.setRequiredWorkExperience(requiredWorkExperience);
        request.setResponsibilities(responsibilities);
        request.setRequirements(requirements);
        request.setConditions(conditions);
        request.setSalaryFrom(salaryFrom);
        request.setSalaryTo(salaryTo);

        try {
            validateRequest(request);
        } catch (ValidationException ex) {
            execution.setVariable("VACANCY_CREATION_ERROR", ex.getMessage());
            throw new BpmnError("VACANCY_CREATION_ERROR", ex.getMessage());
        } catch (Exception ex) {
            execution.setVariable("VACANCY_CREATION_ERROR", "An error occurred while creating the vacancy.");
            throw new BpmnError("VACANCY_CREATION_ERROR", "An error occurred while creating the vacancy.");
        }
    }

    private void validateRequest(VacancyCreateRequest request) throws ValidationException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<VacancyCreateRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<VacancyCreateRequest> violation : violations) {
                errorMessages.append(violation.getMessage()).append("; ");
            }
            throw new ValidationException(errorMessages.toString());
        }
    }
}
