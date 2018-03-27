package com.cognitivecare4u.cognitiveapi;

import java.io.Serializable;

public class FieldError implements Serializable {

    private final String field;
    private final String errorMessage;

    private FieldError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public static FieldError of(String field, String errorMessage) {
        return new FieldError(field, errorMessage);
    }

    public String getField() {
        return field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
