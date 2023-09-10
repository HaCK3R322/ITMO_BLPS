package com.androsov.itmo_blps.configuration.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class XmlFileAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if ("worker1".equals(username) && "123".equals(password)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_WORKER"));

            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        } else if("hr1".equals(username)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_HR"));

            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        }

        Logger LOGGER = Logger.getLogger(XmlFileAuthenticationProvider.class.getName());
        LOGGER.log(Level.WARNING, "In xml auth you!");

        // If authentication fails, return null or throw an AuthenticationException.
        return null;
    }

    // This method is used by Spring Security to determine if this provider supports the given authentication token.
    @Override
    public boolean supports(Class<?> authentication) {
        // This provider only supports UsernamePasswordAuthenticationToken.

        Logger LOGGER = Logger.getLogger(XmlFileAuthenticationProvider.class.getName());
        LOGGER.log(Level.WARNING, "I think i am registrating... somebody checking be. Btw here is " +
                "info about authentication: " + authentication.toString());

        return true;
    }
}
