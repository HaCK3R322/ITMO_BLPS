package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.model.entities.Vacancy;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.servicies.ResumeService;
import com.androsov.itmo_blps.servicies.ResumeVacancyLinkService;
import com.androsov.itmo_blps.servicies.VacancyService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;

@Named
@Component
@RequiredArgsConstructor
public class TryToAttachResumeToVacancy implements JavaDelegate {
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final ResumeVacancyLinkService resumeVacancyLinkService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            Long resumeId = Long.parseLong((String) execution.getVariable("resumeId"));
            Long vacancyId = Long.parseLong((String) execution.getVariable("vacancyId"));

            Resume resume = resumeService.getById(resumeId);
            Vacancy vacancy = vacancyService.getById(vacancyId);

            resumeVacancyLinkService.create(resume, vacancy);
        } catch (Exception e) {
            execution.setVariable("ERROR", e.getMessage());
            throw new BpmnError("ERROR", e.getMessage());
        }
    }
}
