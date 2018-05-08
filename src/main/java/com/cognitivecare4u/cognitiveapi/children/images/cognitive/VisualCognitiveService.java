package com.cognitivecare4u.cognitiveapi.children.images.cognitive;

import com.cognitivecare4u.cognitiveapi.children.analyse.ChildAnalyze;
import com.cognitivecare4u.cognitiveapi.visual_cognition.ClassificationResult;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public interface VisualCognitiveService {

    void listClassifiers();

    ClassificationResult classify(InputStream image);

    ChildAnalyze analyze(InputStream image);
}
