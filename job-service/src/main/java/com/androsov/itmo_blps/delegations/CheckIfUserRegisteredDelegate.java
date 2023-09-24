package com.androsov.itmo_blps.delegations;

import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@Named
@RequiredArgsConstructor
public class CheckIfUserRegisteredDelegate implements JavaDelegate {
    private final UserService userService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");

        try {
            userService.getByUsername(username);
            execution.setVariable("isRegistered", true);
        } catch (Exception ex) {
            execution.setVariable("isRegistered", false);
        }
    }
}
