package com.androsov.itmo_blps.configuration.security;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
@Getter
@Setter
public class XmlFileAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Load user details from the UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Check if the provided password matches the user's password
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            // Passwords don't match, throw an AuthenticationException
            throw new BadCredentialsException("Invalid username or password");
        }
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
