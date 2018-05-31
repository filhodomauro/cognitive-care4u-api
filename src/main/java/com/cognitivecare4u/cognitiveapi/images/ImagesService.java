package com.cognitivecare4u.cognitiveapi.images;

import com.cognitivecare4u.cognitiveapi.analyzes.Analyze;
import com.cognitivecare4u.cognitiveapi.images.cognitive.ClassificationResult;
import com.cognitivecare4u.cognitiveapi.images.cognitive.VisualCognitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

@Service
public class ImagesService {

    private static final String HAPPY = "happy";
    private static final String SAD = "sad";

    @Autowired
    private VisualCognitiveService visualCognitiveService;

    public Analyze analyze(InputStream image) {
        ClassificationResult classificationResult = visualCognitiveService.classify(image);
        Analyze childAnalyze = new Analyze();
        Optional.of(classificationResult.getClasses()).ifPresent(classifierClasses -> {
            classifierClasses.forEach(classifierClass -> {
                if (HAPPY.equalsIgnoreCase(classifierClass.getName())) {
                    childAnalyze.setHappy(classifierClass.getScore());
                } else if (SAD.equalsIgnoreCase(classifierClass.getName())) {
                    childAnalyze.setSad(classifierClass.getScore());
                }
            });
        });
        return childAnalyze;
    }
}
