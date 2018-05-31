package com.cognitivecare4u.cognitiveapi.analyzes;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnalyzeRequest {

    @NotBlank
    private String childId;
    @NotBlank
    private String resourcePath;
    @NotBlank
    private String contentType;
    private String resourceOrigin;
}
