package com.androsov.itmo_blps.controllers;

import com.androsov.itmo_blps.annotations.FailOnGetParams;
import com.androsov.itmo_blps.servicies.AdministrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@AllArgsConstructor
public class AdministrationController {
    private AdministrationService administrationService;

    @PostMapping(path = "/delete/user/{userId}")
    @FailOnGetParams
    @PreAuthorize("hasAnyAuthority('USER_MANIPULATION')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId, HttpServletRequest request) {
        administrationService.deleteUserById(userId);

        return ResponseEntity.ok().body("Success.");
    }
}
