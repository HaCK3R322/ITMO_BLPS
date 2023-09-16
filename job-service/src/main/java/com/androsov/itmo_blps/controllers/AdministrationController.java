package com.androsov.itmo_blps.controllers;

import com.androsov.itmo_blps.annotations.FailOnGetParams;
import com.androsov.itmo_blps.dto.responses.WorkerUserDeletingInfoResponse;
import com.androsov.itmo_blps.model.WorkerUserDeletingInfo;
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
    public ResponseEntity<?> deleteWorkerUserById(@PathVariable Long userId, HttpServletRequest request) {
        WorkerUserDeletingInfo workerUserDeletingInfo = administrationService.deleteWorkerUserById(userId);
        WorkerUserDeletingInfoResponse workerUserDeletingInfoResponse = conversionService.convert(
                workerUserDeletingInfo, WorkerUserDeletingInfoResponse.class
        );

        return ResponseEntity.ok().body(workerUserDeletingInfoResponse);
    }
}
