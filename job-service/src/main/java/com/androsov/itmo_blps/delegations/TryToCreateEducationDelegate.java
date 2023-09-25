package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.dto.requests.EducationCreateRequest;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.servicies.EducationService;
import com.androsov.itmo_blps.servicies.ResumeService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.time.LocalDate;

@Named
@Component
@RequiredArgsConstructor
public class TryToCreateEducationDelegate implements JavaDelegate {
    private final EducationService educationService;
    private final ResumeService resumeService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            String level = (String) execution.getVariable("level");
            String form = (String) execution.getVariable("form");
            LocalDate endDate = LocalDate.parse(((String) execution.getVariable("endDate")));
            String universityName = (String) execution.getVariable("universityName");
            String faculty = (String) execution.getVariable("faculty");
            String specialization = (String) execution.getVariable("specialization");

            EducationCreateRequest request = new EducationCreateRequest(
                    level,
                    form,
                    endDate,
                    universityName,
                    faculty,
                    specialization
            );

            Long resumeId = Long.parseLong((String) execution.getVariable("resumeId"));
            Resume resume = resumeService.getById(resumeId);

            educationService.createFromRequest(resume, request);
        } catch (Exception e) {
            execution.setVariable("ERROR", e.getMessage());
            throw new BpmnError("ERROR", e.getMessage());
        }
    }
}
