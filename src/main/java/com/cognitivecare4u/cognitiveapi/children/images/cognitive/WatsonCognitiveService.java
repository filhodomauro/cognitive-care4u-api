package com.cognitivecare4u.cognitiveapi.children.images.cognitive;

import com.cognitivecare4u.cognitiveapi.visual_cognition.ClassificationResult;
import com.cognitivecare4u.cognitiveapi.visual_cognition.ClassifierClass;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.Classifiers;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Component
public class WatsonCognitiveService implements CognitiveService {

    @Value("${care4u.images.watson.api_key}")
    private String apiKey;

    @Value("${care4u.images.watson.version}")
    private String version;

    @Value("${care4u.images.watson.classifier_id}")
    private String classifierId;

    private VisualRecognition service;

    @PostConstruct
    public void configure() {
        this.service = new VisualRecognition(this.version, this.apiKey);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Watson-Learning-Opt-Out", "true");
        this.service.setDefaultHeaders(headers);
    }

    @Override
    public void listClassifiers() {
        this.service.listClassifiers().enqueue(new ServiceCallback<Classifiers>() {
            @Override
            public void onResponse(Classifiers response) {
                response.getClassifiers().forEach(classifier -> log.error(classifier.getName()));
            }

            @Override
            public void onFailure(Exception e) {
                log.error("Erro to list classifiers", e);
            }
        });
        Classifiers classifiers = this.service.listClassifiers().execute();
        if (classifiers.getClassifiers().isEmpty()) {
            log.error("Empty list");
        }

        classifiers.getClassifiers().forEach(classifier -> log.error(classifier.getName()));
    }

    @Override
    public ClassificationResult classify(InputStream image) {
        ClassificationResult result = new ClassificationResult();
        result.setClassifierId(this.classifierId);
        result.setClasses(new ArrayList<>());
        ClassifyOptions options = new ClassifyOptions.Builder()
                .imagesFile(image)
                .addClassifierId(this.classifierId)
                .build();
        this.service.classify(options).enqueue(new ServiceCallback<ClassifiedImages>() {
            @Override
            public void onResponse(ClassifiedImages response) {
                first(response.getImages()).ifPresent(classifiedImage -> {
                    first(classifiedImage.getClassifiers()).ifPresent(classifierResult -> {
                        classifierResult.getClasses().forEach(classResult -> {
                            result.getClasses().add(
                                    new ClassifierClass(
                                            classResult.getClassName(),
                                            classResult.getScore()
                                    )
                            );
                        });
                    });
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        result.setHighestScore(
                Collections.max(result.getClasses(), (o1, o2) -> Float.compare(o1.getScore(), o2.getScore()))
        );
        return result;
    }

    private <T> Optional<T> first(List<T> collection) {
        if (collection.isEmpty()) {
            return java.util.Optional.empty();
        }
        return Optional.of(collection.get(0));
    }

}
