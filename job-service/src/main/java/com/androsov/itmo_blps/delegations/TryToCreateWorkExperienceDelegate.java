package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.dto.requests.WorkExperienceCreateRequest;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.servicies.ResumeService;
import com.androsov.itmo_blps.servicies.WorkExperienceService;
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
public class TryToCreateWorkExperienceDelegate implements JavaDelegate {
    private final ResumeService resumeService;
    private final WorkExperienceService workExperienceService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            String position = (String) execution.getVariable("position");
            String organizationName = (String) execution.getVariable("organizationName");
            String website = (String) execution.getVariable("website");
            String city = (String) execution.getVariable("city");
            String companyActivity = (String) execution.getVariable("companyActivity");
            String responsibilities = (String) execution.getVariable("responsibilities");
            String achievements = (String) execution.getVariable("achievements");
            LocalDate startDate = LocalDate.parse((String) execution.getVariable("startDate"));
            LocalDate endDate = LocalDate.parse((String) execution.getVariable("endDate"));

            WorkExperienceCreateRequest request = new WorkExperienceCreateRequest(
                    position,
                    organizationName,
                    website,
                    city,
                    companyActivity,
                    responsibilities,
                    achievements,
                    startDate,
                    endDate
            );

            Long resumeId = Long.parseLong((String) execution.getVariable("resumeId"));

            Resume resume = resumeService.getById(resumeId);

            workExperienceService.createFromRequest(resume, request);
        } catch (Exception e) {
            execution.setVariable("ERROR", e.getMessage());
            throw new BpmnError("ERROR", e.getMessage());
        }
    }
}
