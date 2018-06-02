package com.cognitivecare4u.cognitiveapi.speeches.cognitive.watson;

import com.cognitivecare4u.cognitiveapi.analyzes.Analyze;
import com.cognitivecare4u.cognitiveapi.speeches.cognitive.CognitiveNaturalLanguage;
import com.cognitivecare4u.cognitiveapi.types.Classes;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifyOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Primary
@Slf4j
@Component
public class WatsonNaturalLanguageCustom implements CognitiveNaturalLanguage {

    private final NaturalLanguageClassifier naturalLanguageClassifier;

    @Value("${care4u.natural_language_classifier_custom.watson.classifier_id}")
    private String classifierId;

    @Autowired
    public WatsonNaturalLanguageCustom(
            @Value("${care4u.natural_language_classifier_custom.watson.username}") String username,
            @Value("${care4u.natural_language_classifier_custom.watson.password}") String password) {
         this.naturalLanguageClassifier = new NaturalLanguageClassifier(username, password);
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Watson-Learning-Opt-Out", "true");
        this.naturalLanguageClassifier.setDefaultHeaders(headers);
    }

    @Override
    public Analyze analyze(String speech) {
        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .classifierId(this.classifierId)
                .text(speech)
                .build();
        Classification classification = this.naturalLanguageClassifier.classify(classifyOptions).execute();
        System.out.println(classification);
        Analyze analyze = new Analyze();
        Optional.ofNullable(classification.getClasses())
                .ifPresent(classifiedClasses -> {
                    classifiedClasses.forEach(classifiedClass -> {
                        Classes classType = Classes.fromClassifierClass(classifiedClass.getClassName());
                        switch (classType) {
                            case HAPPY:
                                analyze.setHappy(classifiedClass.getConfidence().floatValue());
                                break;
                            case SAD:
                                analyze.setSad(classifiedClass.getConfidence().floatValue());
                                break;
                            case BULLYING:
                                analyze.setBullying(classifiedClass.getConfidence().floatValue());
                                break;
                            case PROFANITY:
                                analyze.setProfanity(classifiedClass.getConfidence().floatValue());
                                break;
                            case SEXUAL_CONTENT:
                                analyze.setSexualContent(classifiedClass.getConfidence().floatValue());
                                break;
                            default:
                                log.info("Classifier class not found: [{}]",classifiedClass);
                        }
                    });
        });
        return analyze;
    }
}
