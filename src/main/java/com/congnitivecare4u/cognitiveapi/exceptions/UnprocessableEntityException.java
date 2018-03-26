package com.congnitivecare4u.cognitiveapi.exceptions;


import com.congnitivecare4u.cognitiveapi.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnprocessableEntityException extends RuntimeException {
    private final List<FieldError> fieldErrors;

    public UnprocessableEntityException(String message, FieldError... fieldErrors) {
        super(message);
        this.fieldErrors = Arrays.asList(fieldErrors);
    }

    public UnprocessableEntityException(String message, List<org.springframework.validation.FieldError> objectErrors) {
        super(message);
        List<FieldError> fieldErrors =
        objectErrors.stream()
                .map(error -> FieldError.of(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
