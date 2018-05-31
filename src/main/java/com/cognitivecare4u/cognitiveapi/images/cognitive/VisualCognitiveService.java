package com.cognitivecare4u.cognitiveapi.images.cognitive;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public interface VisualCognitiveService {

    void listClassifiers();

    ClassificationResult classify(InputStream image);
}
