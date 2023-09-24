package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

@Named
@Component
@RequiredArgsConstructor
public class CheckIfUserIsAuthorizedDelegate implements JavaDelegate {
    private final UserService userService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        final String IS_AUTHORIZED = "isAuthorized";

        try {
            userService.getCurrentUser();
            execution.setVariable(IS_AUTHORIZED, true);
        } catch (EntityNotFoundException ex) {
            execution.setVariable(IS_AUTHORIZED, false);
        }
    }
}
