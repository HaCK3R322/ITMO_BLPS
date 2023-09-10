package com.androsov.itmo_blps.controllers;


import com.androsov.itmo_blps.dto.requests.UserRegistrationRequest;
import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.repositories.UserRepository;
import com.androsov.itmo_blps.servicies.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@CrossOrigin
@AllArgsConstructor
public class RegistrationController {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class.getName());

    private final UserService userService;

    @GetMapping("/register")
    public String registrationForm() {
        return "POST here username and password duh";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String username,
                                          @RequestParam String password,
                                          @RequestParam String roleName) {

        UserRegistrationRequest request = new UserRegistrationRequest(username,
                password,
                roleName);

        User user = userService.registerFromRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping(path = "/me")
    public User getMe() {
        return userService.getCurrentUser();
    }
}

