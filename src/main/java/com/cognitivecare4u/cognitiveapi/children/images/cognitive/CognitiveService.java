package com.cognitivecare4u.cognitiveapi.children.images.cognitive;

import com.cognitivecare4u.cognitiveapi.visual_cognition.ClassificationResult;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public interface CognitiveService {

    void listClassifiers();

    ClassificationResult classify(InputStream image);
}
