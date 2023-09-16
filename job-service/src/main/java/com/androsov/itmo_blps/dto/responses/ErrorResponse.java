package com.androsov.itmo_blps.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private String message;
    private List<String> errors;

    public ErrorResponse(String message) {
        this.message = message;
        errors = new ArrayList<>();
    }
}
