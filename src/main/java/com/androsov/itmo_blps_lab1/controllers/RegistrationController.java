package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.entities.User;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import com.androsov.itmo_blps_lab1.servicies.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@AllArgsConstructor
public class RegistrationController {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class.getName());

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/register")
    public String registrationForm() {
        return "POST here username and password duh";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password) {
        User user = new User(null, username, password);
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }


        LOGGER.log(Level.INFO, user.toString());

        userService.save(user);
        return "Registered!";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public String login(@ModelAttribute User user) throws Exception {
        if(!userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that username not found");
        }

        String dbPassword = userRepository.getByUsername(user.getUsername()).getPassword();
        String gotPassword = user.getPassword();

        // check by BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(gotPassword, dbPassword)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new Exception("Wrong password");
        }

        return "authenticated successfully!";
    }
}

