package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.servicies.AdministrationService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Component
@RequiredArgsConstructor
public class DeleteUserByIdTransactionDelegate implements JavaDelegate {
    private final AdministrationService administrationService;


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long userId = Long.parseLong((String) execution.getVariable("userId"));

        try {
            administrationService.deleteWorkerUserById(userId);
        } catch (Exception e) {
            execution.setVariable("TRANSACTION_EXCEPTION", e.getMessage());
            throw new BpmnError("TRANSACTION_EXCEPTION", e.getMessage());
        }
    }
}
