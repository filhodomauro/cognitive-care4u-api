package com.congnitivecare4u.cognitiveapi;

import com.congnitivecare4u.cognitiveapi.exceptions.NotFoundException;
import com.congnitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
@ControllerAdvice
public class HandlerError {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void notFoundExceptionHandler() {
    }

    @ResponseBody
    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    List<FieldError> unprocessableEntityHandler(UnprocessableEntityException ex) {
        log.error(ex.getMessage());
        return ex.getFieldErrors();
    }
}
