package com.cognitivecare4u.cognitiveapi.children.analyse;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChildAnalyzeRequest {

    @NotBlank
    private String childId;
    @NotBlank
    private String resourcePath;
    @NotBlank
    private String contentType;
    private String resourceOrigin;
}
