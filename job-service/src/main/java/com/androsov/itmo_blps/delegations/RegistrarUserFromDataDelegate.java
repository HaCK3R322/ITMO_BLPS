package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.dto.requests.UserRegistrationRequest;
import com.androsov.itmo_blps.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;

@Component
@Named
@RequiredArgsConstructor
public class RegistrarUserFromDataDelegate implements JavaDelegate {
    private final UserService userService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");
        String password = (String) execution.getVariable("password");
        String roleName = (String) execution.getVariable("ROLE_NAME");

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setRoleName(roleName);

        userService.registerFromRequest(request);
    };
}
