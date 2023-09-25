package com.androsov.itmo_blps.delegations;


import ch.qos.logback.core.boolex.Matcher;
import com.androsov.itmo_blps.configuration.security.SecurityConfiguration;
import com.androsov.itmo_blps.configuration.security.XmlFileAuthenticationProvider;
import com.androsov.itmo_blps.servicies.UserService;
import com.androsov.itmo_blps.servicies.XmlFileUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Named;

@Named
@Component
@RequiredArgsConstructor
public class AuthenticateUserFromDataDelegate implements JavaDelegate {
    private final XmlFileUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String username = (String) execution.getVariable("username");
        String password = (String) execution.getVariable("password");

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            execution.setVariable("success", true);
        } else {
            execution.setVariable("success", false);
        }
    }
}
