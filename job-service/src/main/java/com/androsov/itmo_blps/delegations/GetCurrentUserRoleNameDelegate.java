package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;

@Named
@Component
@RequiredArgsConstructor
public class GetCurrentUserRoleNameDelegate implements JavaDelegate {
    private final UserService userService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            User user = userService.getCurrentUser();
            execution.setVariable("ROLE_NAME", user.getRole().getName());
        } catch (Exception e) {
            execution.setVariable("ROLE_NAME", "none");
        }
    }
}
