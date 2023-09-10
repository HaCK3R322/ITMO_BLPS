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

//    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @ResponseBody
//    public String login(@RequestParam String username,
//                        @RequestParam String password) throws Exception {
//        if(!userRepository.existsByUsername(username)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that username not found");
//        }
//
//        User user = userService.getByUsername(username);
//
//        String dbPassword = userRepository.getByUsername(user.getUsername()).getPassword();
//
//        // check by BCryptPasswordEncoder
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        if (encoder.matches(password, dbPassword)) {
//            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
//                    user.getPassword(),
//                    user.getRole().getPrivileges().stream()
//                            .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
//                            .collect(Collectors.toList()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } else {
//            throw new Exception("Wrong password");
//        }
//
//        return "authenticated successfully!";
//    }
}

