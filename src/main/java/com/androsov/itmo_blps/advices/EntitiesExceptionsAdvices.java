package com.androsov.itmo_blps.advices;

import com.androsov.itmo_blps.dto.responses.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class EntitiesExceptionsAdvices {
    @ExceptionHandler({
            EntityNotFoundException.class,
            IllegalArgumentException.class,
            NullPointerException.class,
            ConstraintViolationException.class,
            AccessDeniedException.class,
            DataIntegrityViolationException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = ex.getMessage();

        if(errorMessage == null)
            return ResponseEntity.badRequest().body(new ErrorResponse("Json parse unknown error."));

        Pattern pattern = Pattern.compile("Unrecognized field \"(.*?)\"");
        Matcher matcher = pattern.matcher(errorMessage);

        List<String> unrecognizedFields = new ArrayList<>();
        if (matcher.find()) {
            unrecognizedFields.add(matcher.group());
        }

        errorMessage = "JSON Parse error.";

        if (unrecognizedFields.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(errorMessage + " Did you even sent something?"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(errorMessage, unrecognizedFields));
        }
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        final List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            errorMessages.add(fieldError.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse("Validation Failed", errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
