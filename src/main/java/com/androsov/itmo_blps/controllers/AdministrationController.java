package com.androsov.itmo_blps.controllers;

import com.androsov.itmo_blps.annotations.FailOnGetParams;
import com.androsov.itmo_blps.dto.responses.UserDeletingInfoResponse;
import com.androsov.itmo_blps.model.UserDeletingInfo;
import com.androsov.itmo_blps.servicies.AdministrationService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@AllArgsConstructor
public class AdministrationController {
    private AdministrationService administrationService;
    private final ConversionService conversionService;

    @PostMapping(path = "/delete/user/{userId}")
    @FailOnGetParams
    @PreAuthorize("hasAnyAuthority('USER_MANIPULATION')")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId, HttpServletRequest request) {
        UserDeletingInfo userDeletingInfo = administrationService.deleteUserById(userId);
        UserDeletingInfoResponse userDeletingInfoResponse = conversionService.convert(
                userDeletingInfo, UserDeletingInfoResponse.class
        );

        return ResponseEntity.ok().body(userDeletingInfoResponse);
    }
}
