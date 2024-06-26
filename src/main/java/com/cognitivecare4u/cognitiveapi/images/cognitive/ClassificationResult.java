package com.cognitivecare4u.cognitiveapi.images.cognitive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationResult {

    private String classifierId;

    private ClassifierClass highestScore;

    private List<ClassifierClass> classes;
}
