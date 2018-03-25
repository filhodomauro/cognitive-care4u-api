package com.congnitivecare4u.cognitiveapi.exceptions;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class UnprocessableEntityException extends RuntimeException {
    private final List<FieldError> errors;

    public UnprocessableEntityException(String message, List<FieldError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<FieldError> getErrors() {
        return errors;
    }
}
