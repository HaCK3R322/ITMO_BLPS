package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.servicies.ResumeService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

@Named
@Component
@RequiredArgsConstructor
public class CheckIfCurrentUserHasAccessToResumeDelegate implements JavaDelegate {
    private final ResumeService resumeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long resumeId = Long.parseLong((String) execution.getVariable("resumeId"));

        try {
            resumeService.getById(resumeId);
            execution.setVariable("hasAccessToResume", true);
        } catch (Exception e) {
            execution.setVariable("hasAccessToResume", false);
        }
    }
}
