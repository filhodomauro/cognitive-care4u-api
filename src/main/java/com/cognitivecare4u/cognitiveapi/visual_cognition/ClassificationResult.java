package com.cognitivecare4u.cognitiveapi.visual_cognition;

import com.cognitivecare4u.cognitiveapi.visual_cognition.ClassifierClass;
import lombok.Data;

import java.util.List;

@Data
public class ClassificationResult {

    private String classifierId;

    private ClassifierClass highestScore;

    private List<ClassifierClass> classes;
}
